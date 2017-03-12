package tools.input;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ExtendedInputStream {
	private InputStream inputStream;
	
	public ExtendedInputStream(InputStream inputStream) throws IOException {
		this.inputStream = inputStream;
	}
	
	public int read() throws IOException {
		return inputStream.read() & 0xFF;
	}
	
	public int readInt() throws IOException {
		int byte0 = read();
		int byte1 = read();
		int byte2 = read();
		int byte3 = read();
		
		return (byte3 << 24) + (byte2 << 16) + (byte1 << 8) + byte0;
	}
	
	public void readBytes(byte[] arr, int offset, int length) throws IOException {
		for (int i = offset; i < offset + length; i++) {
			arr[i] = (byte) read();
		}
	}
	
	public byte[] readBytes(int n) throws IOException {
		byte[] bytes = new byte[n];
		
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) read();
		}
		
		return bytes;
	}
	
	public String readNullTerminatedString() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		byte b;
		while ((b = (byte) read()) != 0) {
			baos.write(b);
		}
		
		return new String(baos.toByteArray());
	}
}
