package net;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import lobby.handlers.JoinLobbyHandler;
import log.Log;
import login.handlers.LoginHandler;
import tools.HexTools;
import tools.input.ExtendedInputStream;

public class UserTCPSession implements Runnable {
	protected GenericTCPServer server;
	protected Socket socket;
	protected DataOutputStream output;
	protected ExtendedInputStream input;
	
	public int clientState = -1;
	public int serverState = -1;
	
	protected User user;
	
	public UserTCPSession(GenericTCPServer server, Socket socket) throws IOException {
		this.server = server;
		this.socket = socket;
		input = new ExtendedInputStream(socket.getInputStream());
		output = new DataOutputStream(socket.getOutputStream());
		user = new User();
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
				
				// Decide which encryption to use based on the login packet
				if (user.encryptionVersion == 0) {
					Cryptography.decryptMessage(1, messageBytes);
					
					// If you decrypt it with the normal decryption algorithm and get the right message
					if (HexTools.getIntegerInByteArray(messageBytes, 4) == LoginHandler.REQUEST_ID || HexTools.getIntegerInByteArray(messageBytes, 4) == JoinLobbyHandler.REQUEST_ID) {
						// then set the encryption version to 1
						user.encryptionVersion = 1;
					}
					else {
						// otherwise, we're on the second encryption algorithm
						user.encryptionVersion = 2;
					}
					
					// Encrypt again
					Cryptography.encryptMessage(1, messageBytes);
				}
				
				Cryptography.decryptMessage(user.encryptionVersion, messageBytes);
				
				int messageID = HexTools.getIntegerInByteArray(messageBytes, 4);
				int stateFromMessage = HexTools.getIntegerInByteArray(messageBytes, 16);
				
				
				GenericHandler message = server.processPacket(this, messageID, messageBytes);
				
				if (message == null) {
					Log.log(server.getName() + ": New client packet: 0x" + HexTools.integerToHexString(messageID) + " - unimplemented yet");
				}
				else {
					Log.log(server.getName() + ": New client packet: 0x" + HexTools.integerToHexString(messageID) + " - " + message.getClass().getCanonicalName());
				}
				changeStateWhenRecieve(stateFromMessage);
				
				if (message != null) {
					byte[] response = message.getResponse();
					
					// Response can be null if there is no response (not implemented)
					if (response != null) {
						sendMessage(response);
					}
					
					message.afterSend();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(byte[] response) throws IOException {
		// Change the validator
		HexTools.putIntegerInByteArray(response, 0x8, 0x2B1C);
				
		// Change the state
		HexTools.putIntegerInByteArray(response, 16, clientState);
		
		// Change the checksum
		HexTools.putIntegerInByteArray(response, 12, Cryptography.getDigest(response));
		
//		HexTools.printHexArray(response, false);
//		System.out.println();
//		System.out.println();
		
		// Encrypt and send
		Cryptography.encryptMessage(user.encryptionVersion, response);
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
	
	public User getUser() {
		return user;
	}
}
