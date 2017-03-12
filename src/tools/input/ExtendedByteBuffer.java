package tools.input;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExtendedByteBuffer {
	private byte[] arr;
	private int pos;
	
	public ExtendedByteBuffer(File file) throws IOException {
		FileInputStream input = new FileInputStream(file);
		
		arr = new byte[(int) file.length()];
		input.read(arr);
		input.close();
	}
	
	public ExtendedByteBuffer(byte[] buffer) {
		arr = new byte[buffer.length];
		
		for (int i = 0; i < buffer.length; i++) {
			arr[i] = buffer[i];
		}
	}
	
	public void changePosition(int newPosition) {
		this.pos = newPosition;
	}
	
	public int read() {
		return arr[pos++] & 0xFF;
	}
	
	public int readInt() {
		int byte0 = read();
		int byte1 = read();
		int byte2 = read();
		int byte3 = read();
		
		return (byte3 << 24) + (byte2 << 16) + (byte1 << 8) + byte0;
	}
	
	public byte[] readBytes(int n) {
		byte[] bytes = new byte[n];
		
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) read();
		}
		
		return bytes;
	}
	
	public String readNullTerminatedString() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		byte b;
		while ((b = (byte) read()) != 0) {
			baos.write(b);
		}
		
		return new String(baos.toByteArray());
	}
	
	public String readNullTerminatedString(int maxLength) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		byte b;
		while ((b = (byte) read()) != 0) {
			baos.write(b);
		}
		
		pos += maxLength - baos.size();
		
		return new String(baos.toByteArray());
	}
	
	// Returns the next n bytes but doesn't change the position of the buffer.
	public byte[] peekNext(int n) {
		if (pos + n >= arr.length) {
			n = arr.length - pos;
			
			if (n == 0) {
				System.out.println("No more bytes to read");
			}
		}
		
		byte[] bytes = new byte[n];
		
		for (int i = 0; i < n; i++) {
			bytes[i] = arr[pos + i];
		}
		
		return bytes;
	}
}
