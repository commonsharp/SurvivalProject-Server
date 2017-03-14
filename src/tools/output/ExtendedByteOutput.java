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
	
	public void putByte(byte value) {
		buffer.addLast(value);
	}
	
	public void putShort(int value) {
		for (int j = 0; j < 2; j++) {
			putByte((byte) (value >> (j * 8)));
		}
	}
	
	public void putInteger(int value) {
		for (int j = 0; j < 4; j++) {
			putByte((byte) (value >> (j * 8)));
		}
	}
	
	public void putLong(long value) {
		for (int j = 0; j < 8; j++) {
			putByte((byte) (value >> (j * 8)));
		}
	}
	
	public void putBytes(byte[] bytes) {
		for (int i = 0; i < bytes.length; i++) {
			putByte(bytes[i]);
		}
	}
	
	// Assumes s.length <= length
	public void putString(String s, int length) {
		if (s == null) {
			putBytes(new byte[length + 1]);
			return;
		}
		
		for (int i = 0; i < s.length(); i++) {
			putByte((byte) s.charAt(i));
		}
		
		putBytes(new byte[length - s.length() + 1]);
	}
}
