package net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import log.Log;
import tools.HexTools;

public abstract class GenericUDPServer implements Runnable {
	protected int port;
	protected boolean isRunning = true;
	protected String name;
	protected DatagramSocket socket;
	
	protected Thread serverThread;
	
	public abstract ClientGenericMessage processPacket(int messageID, byte[] messageBytes);
	
	public GenericUDPServer(String name, int port) {
		this.name = name;
		this.port = port;
	}
	
	public void startServer() throws IOException {
		serverThread = new Thread(this);
		serverThread.start();
	}
	
	@Override
	public void run() {
		try {
			System.out.println(name + " has started listening.");
			socket = new DatagramSocket(port);
			
			while (isRunning) {
				// need to check maybe there can be bigger packets...
				byte[] buffer = new byte[8192];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				InetAddress ipAddress = packet.getAddress();
				int port = packet.getPort();
				
				int length = HexTools.getIntegerInByteArray(packet.getData(), 0);

				byte[] messageBytes = new byte[length];
				
				for (int i = 0; i < length; i++) {
					messageBytes[i] = packet.getData()[i];
				}
				
				if (length == -1) {
					System.out.println("User disconnected");
					break;
				}
				
				Cryptography.decryptMessage(messageBytes);
				
				int messageID = HexTools.getIntegerInByteArray(messageBytes, 4);
				int state = HexTools.getIntegerInByteArray(messageBytes, 16);
				
				System.out.println("State: " + state);
				Log.log(name + ": New client packet: 0x" + HexTools.integerToHexString(messageID));
				
				ClientGenericMessage message = processPacket(messageID, messageBytes);
				
				if (message != null) {
					byte[] response = message.getResponse();
					
					// Response can be null if there is no response (not implemented)
					if (response != null) {
						sendMessage(state, ipAddress, port, response);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int newState(int oldState) {
		if (oldState == -1)
			return 0;
		
		oldState = (~oldState + 0x14fb) * 0x1f;
		return Math.abs((oldState >> 16) ^ oldState);
	}
	
	public void sendMessage(int state, InetAddress ipAddress, int port, byte[] response) throws IOException {
		// Change the state
		HexTools.putIntegerInByteArray(response, 16, state);
		
		// Change the checksum
		HexTools.putIntegerInByteArray(response, 12, Cryptography.getDigest(response));
		
		// Encrypt and send
		Cryptography.encryptMessage(response);
		DatagramPacket packet = new DatagramPacket(response, response.length, ipAddress, port);
		socket.send(packet);
		
		// Change the state
	}
	
	public void stopServer() throws IOException {
		this.isRunning = false;
	}

	public String getName() {
		return name;
	}
}
