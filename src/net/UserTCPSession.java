package net;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import log.Log;
import tools.HexTools;
import tools.input.ExtendedInputStream;

public class UserTCPSession implements Runnable {
	protected GenericTCPServer server;
	protected Socket socket;
	protected DataOutputStream output;
	protected ExtendedInputStream input;
	
	protected int clientState = -1;
	protected int serverState = -1;
	
	public UserTCPSession(GenericTCPServer server, Socket socket) throws IOException {
		this.server = server;
		this.socket = socket;
		input = new ExtendedInputStream(socket.getInputStream());
		output = new DataOutputStream(socket.getOutputStream());
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				int length = input.readInt();
				
				if (length == -1) {
					System.out.println("User disconnected");
					break;
				}
				
				byte[] messageBytes = new byte[length];
				HexTools.putIntegerInByteArray(messageBytes, 0, length);
				
				// Read the rest of the message
				input.readBytes(messageBytes, 4, length - 4);
				Cryptography.decryptMessage(messageBytes);
				
				int messageID = HexTools.getIntegerInByteArray(messageBytes, 4);
				int stateFromMessage = HexTools.getIntegerInByteArray(messageBytes, 16);
				
				Log.log(server.getName() + ": New client packet: 0x" + HexTools.integerToHexString(messageID));
				
				ClientGenericMessage message = server.processPacket(messageID, messageBytes);
				
				changeStateWhenRecieve(stateFromMessage);
				
				if (message != null) {
					byte[] response = message.getResponse();
					
					// Response can be null if there is no response (not implemented)
					if (response != null) {
						sendMessage(response);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(byte[] response) throws IOException {
		// Change the state
		HexTools.putIntegerInByteArray(response, 16, clientState);
		
		// Change the checksum
		HexTools.putIntegerInByteArray(response, 12, Cryptography.getDigest(response));
		HexTools.printHexArray(response, false);
		// Encrypt and send
		Cryptography.encryptMessage(response);
		output.write(response);
		output.flush();
		
		// Change the state
		clientState = newState(clientState);
	}
	
	public void changeStateWhenRecieve(int stateFromMessage) {
		if (serverState == -1) {
			serverState = stateFromMessage;
			clientState = stateFromMessage;
			clientState = newState(clientState);
			serverState = newState(serverState);
			return; // good
		}

		if (serverState == stateFromMessage) {
			serverState = newState(serverState);
			// good
		}
		else {
			// bad
		}
	}
	
	public static int newState(int oldState) {
		if (oldState == -1)
			return 0;
		
		oldState = (~oldState + 0x14fb) * 0x1f;
		return Math.abs((oldState >> 16) ^ oldState);
	}
}
