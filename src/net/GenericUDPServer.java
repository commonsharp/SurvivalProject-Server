package net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import lobby.LobbyServer;
import net.handlers.GenericHandler;
import net.objects.User;
import tools.ExtendedByteBuffer;
import tools.HexTools;

public abstract class GenericUDPServer implements Runnable {
	protected int port;
	protected boolean isRunning = true;
	protected String name;
	protected DatagramSocket socket;
	
	protected Thread serverThread;
	
	public LobbyServer lobby;
	
	public abstract GenericHandler processPacket(GenericUDPServer udpServer, int messageID, byte[] messageBytes) throws IOException;
	
	public GenericUDPServer(LobbyServer lobby, String name, int port) {
		this.lobby = lobby;
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
				
				int encryptionVersion = 1;
				if (lobby.findUser(ipAddress, port) != null) {
					encryptionVersion = lobby.findUser(ipAddress, port).encryptionVersion;
					Cryptography.decryptMessage(encryptionVersion, messageBytes);
				}
				else {
					Cryptography.decryptMessage(1, messageBytes);
					
					// If you decrypt it with the normal decryption algorithm and don't get the right message
					if (HexTools.getIntegerInByteArray(messageBytes, 4) != 0x1100 && HexTools.getIntegerInByteArray(messageBytes, 4) != 0x1101) {
						// then we're on the second encryption algorithm
						Cryptography.encryptMessage(1, messageBytes);
						Cryptography.decryptMessage(2, messageBytes);
						encryptionVersion = 2;
					}
				}
				
				int messageID = HexTools.getIntegerInByteArray(messageBytes, 4);
				int state = HexTools.getIntegerInByteArray(messageBytes, 16);
				User u;
				if (messageID == 0x1100) {
					ExtendedByteBuffer buf = new ExtendedByteBuffer(messageBytes);
					String username = buf.getString(0x14);
					
					u = lobby.findUser(username);
					
					if (u != null) {
						u.udpIPAddress = ipAddress;
						u.udpPort = port;
						u.udpState = state;
					}
				}
				else if (messageID != 0x1101) {
					u = lobby.findUser(ipAddress, port);
					
					if (u != null) {
						u.udpState = state;
					}
				}
				
				GenericHandler message = processPacket(this, messageID, messageBytes);
//				if (message == null) {
//					Log.log(name + ": New client packet: 0x" + HexTools.integerToHexString(messageID) + " - unimplemented yet");
//				}
//				else {
//					Log.log(name + ": New client packet: 0x" + HexTools.integerToHexString(messageID) + " - " + message.getClass().getCanonicalName());
//				}
				
				if (message != null) {
					message.interpretBytes();
					message.processMessage();
					byte[] response = message.getResponse();
					
					// Response can be null if there is no response (not implemented)
					if (response != null) {
						sendMessage(encryptionVersion, state, ipAddress, port, response);
					}
					
					message.afterSend();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(User user, byte[] response) throws IOException {
		// Change the validator
		HexTools.putIntegerInByteArray(response, 0x8, 0x2B1C);
		
		// Change the state
//		HexTools.putIntegerInByteArray(response, 16, user.udpState);
		
		// Change the checksum
		HexTools.putIntegerInByteArray(response, 12, Cryptography.getDigest(response));
		
		// Encrypt and send
		Cryptography.encryptMessage(user.encryptionVersion, response);
		DatagramPacket packet = new DatagramPacket(response, response.length, user.udpIPAddress, user.udpPort);
		socket.send(packet);
		
		// Change the state
	}
	
	public void sendMessage(int encryptionVersion, int state, InetAddress ipAddress, int port, byte[] response) throws IOException {
		// Change the validator
		HexTools.putIntegerInByteArray(response, 0x8, 0x2B1C);
		
		// Change the state
		HexTools.putIntegerInByteArray(response, 16, state);
		
		// Change the checksum
		HexTools.putIntegerInByteArray(response, 12, Cryptography.getDigest(response));
		
		// Encrypt and send
		Cryptography.encryptMessage(encryptionVersion, response);
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
