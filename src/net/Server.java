package net;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import login.client.messages.LoginRequest;
import tools.HexTools;
import tools.input.ExtendedInputStream;

public class Server {
	public static void main(String[] args) throws IOException {
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
			HexTools.printHexArray(messageBytes, false);
			System.out.println();
			System.out.println();
			int messageID = HexTools.getIntegerInByteArray(messageBytes, 4);
			System.out.println(messageID);
			
			LoginRequest message = new LoginRequest(messageBytes);
			
			byte[] response = message.getResponse();
			
			HexTools.printHexArray(response, false);
			encrypt(response);
			output.write(response);
			output.flush();
		}
		
		server.close();
		
//		byte[] b = {(byte) 0x88, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x07, (byte) 0x27, (byte) 0x00, (byte) 0x00, (byte) 0x1C, (byte) 0x2B, (byte) 0x00, (byte) 0x00, (byte) 0xFA, (byte) 0x7F, (byte) 0x89, (byte) 0xBA, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x19, (byte) 0x4C, (byte) 0xD2, (byte) 0x7E, (byte) 0x69, (byte) 0xC6, (byte) 0x92, (byte) 0x01, (byte) 0xEC, (byte) 0x8B, (byte) 0x36, (byte) 0x7F,
//				(byte) 0xB4, (byte) 0xFA, (byte) 0x33, (byte) 0xBD, (byte) 0x70, (byte) 0x33, (byte) 0xF3, (byte) 0x27, (byte) 0x71, (byte) 0x87, (byte) 0xE6, (byte) 0x32, (byte) 0xD4, (byte) 0x81, (byte) 0x28, (byte) 0x6D, (byte) 0xC0, (byte) 0x3F, (byte) 0x5B, (byte) 0x3A, (byte) 0x78, (byte) 0x30, (byte) 0xC6, (byte) 0x00, (byte) 0x37, (byte) 0x15, (byte) 0x00, (byte) 0x00, (byte) 0x62, (byte) 0x61, (byte) 0x72, (byte) 0x61,
//				(byte) 0x6B, (byte) 0x00, (byte) 0x16, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x6C, (byte) 0x65, (byte) 0x76, (byte) 0x79, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x24, (byte) 0x91, (byte) 0x17, (byte) 0x03, (byte) 0x89, (byte) 0x1B, (byte) 0x00, (byte) 0x00, (byte) 0x20, (byte) 0x03, (byte) 0x58, (byte) 0x02,
//				(byte) 0x20, (byte) 0x03, (byte) 0x58, (byte) 0x02, (byte) 0x00, (byte) 0xF4, (byte) 0x70, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xE0, (byte) 0xFD, (byte) 0x12, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xC6, (byte) 0xF3, (byte) 0xD6, (byte) 0x77, (byte) 0x20, (byte) 0x0D, (byte) 0x81, (byte) 0x00, (byte) 0xC6, (byte) 0xF3, (byte) 0xD6, (byte) 0x77,
//				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x40, (byte) 0x13, (byte) 0x00, (byte) 0x00};
//		
//		HexTools.printHexArray(b, false);
//		encrypt(b);
//		decrypt(b);
//		System.out.println();
//		System.out.println();
//		HexTools.printHexArray(b, false);
	}
	
	public static void encrypt(byte[] buffer) {
		for (int i = 4; i < buffer.length; i++) {
			buffer[i] = (byte) ~(((buffer[i] & 0xFF) << 3) | ((buffer[i] & 0xFF) >> 5));
		}
	}
	
	public static void decrypt(byte[] buffer) {
		for (int i = 4; i < buffer.length; i++) {
			buffer[i] = (byte) ~(((buffer[i] & 0xFF) >> 3) | ((buffer[i] & 0xFF) << 5));
		}
	}
}
