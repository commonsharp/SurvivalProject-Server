package login.server;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import tools.HexTools;

public class Server {
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(21000);
		Socket socket = server.accept();
		
		DataInputStream input = new DataInputStream(socket.getInputStream());
		
//		int length = input.readInt(); // this is big endian... need to change to little endian
		byte[] bytes = new byte[1024];
		input.read(bytes);

//		HexTools.printHexArray(bytes);
		decrypt(bytes);
		HexTools.printHexArray(bytes, true);
//		System.out.println("Welcome!@!~!#@#");
	}
	
	public static void decrypt(byte[] buffer) {
		int sz = buffer.length;
		
		for (int i = 4; i < sz; i++) {
			buffer[i] = (byte) ~(((buffer[i] & 0xFF) >> 3) | ((buffer[i] & 0xFF) << 5));
		}
	}
}
