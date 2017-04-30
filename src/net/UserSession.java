package net;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.SQLException;

import log.Log;
import main.Main;
import net.handlers.GenericHandler;
import net.objects.User;
import tools.HexTools;

public class UserSession implements Runnable {
	protected GenericTCPServer server;
	protected Socket socket;
	protected DataOutputStream output;
	protected InputStream input;
	
	public int clientState = -1;
	public int serverState = -1;
	
	public long timeSinceLastActivity;
	
	public InetAddress udpIPAddress;
	public int udpPort;
	public int encryptionVersion;
	
	protected User user;
	
	public UserSession(GenericTCPServer server, Socket socket) throws IOException {
		this.server = server;
		this.socket = socket;
		input = socket.getInputStream();
		output = new DataOutputStream(socket.getOutputStream());
		user = new User();
		timeSinceLastActivity = System.currentTimeMillis();
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				byte[] lengthBytes = new byte[4];
				lengthBytes[0] = (byte) (input.read() & 0xFF);
				lengthBytes[1] = (byte) (input.read() & 0xFF);
				lengthBytes[2] = (byte) (input.read() & 0xFF);
				lengthBytes[3] = (byte) (input.read() & 0xFF);

				int length = HexTools.getIntegerInByteArray(lengthBytes, 0);
				
				if (length == -1) {
					server.userSessions.remove(this);
					server.onUserDisconnect(this);
					
					System.out.println("User disconnected");
					break;
				}
				
				if (Main.FORCE_LATENCY) {
					try {
						Thread.sleep(Main.FORCE_LATENCY_TIME);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				byte[] messageBytes = new byte[length];
				HexTools.putIntegerInByteArray(messageBytes, 0, length);
				
				// Read the rest of the message
				input.read(messageBytes, 4, length - 4);
				
				// Decide which encryption to use based on the login packet
				if (encryptionVersion == 0) {
					Cryptography.decryptMessage(1, messageBytes);
					
					// If you decrypt it with the normal decryption algorithm and get the right message
					if (HexTools.getIntegerInByteArray(messageBytes, 4) == Messages.LOGIN_CREDENTIALS_REQUEST ||
							HexTools.getIntegerInByteArray(messageBytes, 4) == Messages.JOIN_LOBBY_REQUEST ||
							HexTools.getIntegerInByteArray(messageBytes, 4) == Messages.SERVERS_INFO_REQUEST) {
						// then set the encryption version to 1
						encryptionVersion = 1;
					}
					else {
						// otherwise, we're on the second encryption algorithm
						encryptionVersion = 2;
					}
					
					// Encrypt again
					Cryptography.encryptMessage(1, messageBytes);
				}
				
				Cryptography.decryptMessage(encryptionVersion, messageBytes);
				
				int messageID = HexTools.getIntegerInByteArray(messageBytes, 4);
				int stateFromMessage = HexTools.getIntegerInByteArray(messageBytes, 16);
				
				GenericHandler message = server.processPacket(this, messageID, messageBytes);
				
				if (message == null) {
					Log.log(server.getName() + ": New client packet: 0x" + HexTools.integerToHexString(messageID) + " - unimplemented yet");
				}
				
				changeStateWhenRecieve(stateFromMessage);
				
				if (message != null) {
					message.interpretBytes();
					message.processMessage();
					Log.log(server.getName() + ": New client packet: 0x" + HexTools.integerToHexString(messageID) + " - " + message.getClass().getCanonicalName());
					
					byte[] response = message.getResponse();
					
					// Response can be null if there is no response (not implemented)
					if (response != null) {
						sendMessage(response);
					}
					
					message.afterSend();
				}
			}
		} catch (IOException | SQLException e) {
			e.printStackTrace();
			System.out.println("User disconnected");
			server.userSessions.remove(this);
		}
	}
	
	public void sendMessage(byte[] response) throws IOException {
		if (Main.FORCE_LATENCY) {
			try {
				Thread.sleep(Main.FORCE_LATENCY_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
		Cryptography.encryptMessage(encryptionVersion, response);
		output.write(response);
		output.flush();
		
		// Change the state
		clientState = Cryptography.getNewState(clientState);
	}
	
	public void changeStateWhenRecieve(int stateFromMessage) {
		if (serverState == -1) {
			serverState = stateFromMessage;
			clientState = stateFromMessage;
			clientState = Cryptography.getNewState(clientState);
			serverState = Cryptography.getNewState(serverState);
			return; // good
		}

		if (serverState == stateFromMessage) {
			serverState = Cryptography.getNewState(serverState);
			// good
		}
		else {
			// bad
		}
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		if (user == null || user.getUsername() == null) {
			return "no username";
		}
		
		return user.getUsername();
	}
}
