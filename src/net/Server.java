package net;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import login.client.messages.LoginRequest;
import tools.HexTools;
import tools.input.ExtendedByteBuffer;
import tools.input.ExtendedInputStream;

public class Server {
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		ServerSocket server = new ServerSocket(21000);
		Socket socket = server.accept();
		
		OutputStream output;
		// There's no need to use it... @TODO change it 
		ExtendedInputStream input = new ExtendedInputStream(socket.getInputStream());
		output = socket.getOutputStream();
		
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
			
			System.out.println(Integer.toHexString(message.getChecksum()));
			System.out.println(Integer.toHexString(MD5.getDigest(messageBytes)));
//			System.out.println(getDigest(messageBytes));
//			byte[] response = message.getResponse();
//			
//			HexTools.printHexArray(response, false);
//			output.write(response);
//			output.flush();
		}
		
		server.close();
	}
	
	public static void decrypt(byte[] buffer) {
		int sz = buffer.length;
		
		for (int i = 4; i < sz; i++) {
			buffer[i] = (byte) ~(((buffer[i] & 0xFF) >> 3) | ((buffer[i] & 0xFF) << 5));
		}
	}
}
