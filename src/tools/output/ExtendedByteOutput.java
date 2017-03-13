package tools.output;

import java.util.LinkedList;

import tools.HexTools;

public class ExtendedByteOutput {
	protected LinkedList<Byte> buffer;
	
	public ExtendedByteOutput() {
		buffer = new LinkedList<Byte>();
	}
	
	public int getSize() {
		return buffer.size();
	}
	
	public void getBytes(byte[] bytes, int offset) {
		int i = 0;
		
		for (Byte b : buffer) {
			bytes[offset + i] = b;
			i++;
		}
	}
	
	public void putByte(byte b) {
//		buffer.push(b);
//		buffer.add(b);
		buffer.addLast(b);
	}
	
	public void putInteger(int i) {
		byte[] bytes = HexTools.intToByteArray(i);
		
		for (int j = 0; j < 4; j++) {
			putByte(bytes[j]);
		}
	}
	
	public void putLong(long l) {
		for (int j = 0; j < 8; j++) {
			putByte((byte) (l % 256));
			l /= 256;
		}
	}
	
	public void putBytes(byte[] bytes) {
		for (int i = 0; i < bytes.length; i++) {
			putByte(bytes[i]);
		}
	}
}
