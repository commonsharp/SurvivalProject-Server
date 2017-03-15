package net;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import log.Log;
import login.client.ClientGenericMessage;
import login.client.messages.ServerInfoRequest;
import login.client.messages.SetActiveCharacterRequest;
import login.client.messages.LoginRequest;
import login.client.messages.GuildMarkRequest;
import tools.HexTools;
import tools.input.ExtendedInputStream;

public class Server {
	final static boolean IS_TEST = false;
	public static int clientState = -1;
	public static int serverState = -1;
	
	public static void main(String[] args) throws IOException {
		if (IS_TEST) {
			test();
			return;
		}
		
		ServerSocket server = new ServerSocket(21000);
		
		while (true) {
		Socket socket = server.accept();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					DataOutputStream output;
					// There's no need to use it... @TODO change it 
					ExtendedInputStream input = new ExtendedInputStream(socket.getInputStream());
					output = new DataOutputStream(socket.getOutputStream());
					
					while (true) {
						int length = input.readInt();
						
						if (length == -1) {
							System.out.println("User disconnected");
							break;
						}
						
						byte[] messageBytes = new byte[length];
						HexTools.putIntegerInByteArray(messageBytes, 0, length);
						input.readBytes(messageBytes, 4, length - 4);
						decrypt(messageBytes);
						int messageID = HexTools.getIntegerInByteArray(messageBytes, 4);
						Log.log("New client packet: 0x" + HexTools.integerToHexString(messageID));
						ClientGenericMessage message = null;
						
						int stateFromMessage = HexTools.getIntegerInByteArray(messageBytes, 16);
						System.out.println("State from message:" + stateFromMessage);
						
						switch (messageID) {
						case 0x2707:
							message = new LoginRequest(messageBytes);
							break;
						case 0x2907:
							message = new ServerInfoRequest(messageBytes);
							break;
						case 0x2911:
							message = new SetActiveCharacterRequest(messageBytes);
							break;
//						case 0x2917:
//							message = new GuildMarkRequest(messageBytes);
//							break;
						default:
							HexTools.printHexArray(messageBytes, 20, false);
							System.out.println();
							HexTools.printHexArray(messageBytes, 20, true);
						}
						
	//					Server responses... X_X
	//					case 0x2807:
	//						break;
	//					case 0x2908:
	//						break;
	//					case 0x2912:
	//						break;
	//					case 0x2916:
	//						break;
	//					case 0x2918:
	//						break;
	//					case 0x2919:
	//						break;
	//					case 0x2922:
	//						break;
	//					case 0x2923:
	//				        break;
	//				    case 0x2924:
	//				    	break;
	//				    case 0x2925:
	//				    	break;
						
						
						whenReceived(stateFromMessage);
						
						if (message != null) {
							byte[] response = message.getResponse();
							
							// Response can be null if there is no response
							if (response != null) {
								HexTools.putIntegerInByteArray(response, 16, clientState);
								HexTools.printHexArray(response, false);
								HexTools.putIntegerInByteArray(response, 12, MD5.getDigest(response));
								encrypt(response);
								output.write(response);
								output.flush();
								clientState = newState(clientState);
							}
						}
					}
				} catch (IOException e) {
					
				}
			}
		}).start();
		
		}
		
//		server.close();
	}
	
	public static void whenReceived(int stateFromMessage) {
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
	
	public static int changeState(int previousState) {
		return Math.abs(((previousState = (~previousState + 0x14fb) * 0x1f) >> 16) ^ previousState);
	}
	
	public static void test() {
		int oldState = 12345678;
		System.out.println(newState(oldState));
//		int newState = 31 * (~oldState) ^ (31 * (~oldState) >> 16);
//		System.out.println(newState);
	}
	
	public static void encrypt(byte[] buffer) {
		for (int i = 4; i < buffer.length; i++) {
			int c = ~(buffer[i] & 0xFF) & 0xFF;
			buffer[i] = (byte) (((c >> 5) & 0xFF) | ((c << 3) & 0xFF));
//			buffer[i] = (byte) ~(((buffer[i] & 0xFF) << 3) | ((buffer[i] & 0xFF) >> 5));
		}
	}
	
	public static void decrypt(byte[] buffer) {
		for (int i = 4; i < buffer.length; i++) {
			buffer[i] = (byte) ~(((buffer[i] & 0xFF) >> 3) | ((buffer[i] & 0xFF) << 5));
		}
	}
}
