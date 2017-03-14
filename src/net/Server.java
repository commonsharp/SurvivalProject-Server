package net;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import log.Log;
import login.client.ClientGenericMessage;
import login.client.messages.LoginRequest;
import login.client.messages.ServerInfoRequest;
import tools.HexTools;
import tools.input.ExtendedInputStream;

public class Server {
	final static boolean IS_TEST = false;
	public static void main(String[] args) throws IOException {
		if (IS_TEST) {
			test();
			return;
		}
		
		ServerSocket server = new ServerSocket(21000);
		Socket socket = server.accept();
		
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
			
			switch (messageID) {
			case 0x2707:
				message = new LoginRequest(messageBytes);
				break;
			case 0x2907:
				message = new ServerInfoRequest(messageBytes);
				break;
			case 0x2917:
				break;
			default:
				HexTools.printHexArray(messageBytes, 20, false);
				System.out.println();
				HexTools.printHexArray(messageBytes, 20, true);
			}
			
//			Server responses... X_X
//			case 0x2807:
//				break;
//			case 0x2908:
//				break;
//			case 0x2912:
//				break;
//			case 0x2916:
//				break;
//			case 0x2918:
//				break;
//			case 0x2919:
//				break;
//			case 0x2922:
//				break;
//			case 0x2923:
//		        break;
//		    case 0x2924:
//		    	break;
//		    case 0x2925:
//		    	break;
			
			if (message != null) {
				byte[] response = message.getResponse();
				HexTools.printHexArray(response, false);
				encrypt(response);
				output.write(response);
				output.flush();
			}
		}
		
		server.close();
	}
	
	public static void test() {
		byte[] b = {(byte) 0xA8, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x07, (byte) 0x28, (byte) 0x00, (byte) 0x00, (byte) 0x1C, (byte) 0x2B, (byte) 0x00, (byte) 0x00, (byte) 0xA1, (byte) 0x66, (byte) 0xC5, (byte) 0x96, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x1E, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x1E, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0C, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x62, (byte) 0x61, (byte) 0x72, (byte) 0x61, (byte) 0x6B, (byte) 0x67, (byte) 0x75, (byte) 0x69, (byte) 0x6C, (byte) 0x64, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
		HexTools.printHexArray(b, false);
		encrypt(b);
		decrypt(b);
		System.out.println();
		HexTools.printHexArray(b, false);
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
