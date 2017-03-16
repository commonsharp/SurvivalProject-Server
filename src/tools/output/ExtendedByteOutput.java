package tools.output;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ExtendedByteOutput {
	protected ByteBuffer buffer;
	
	public ExtendedByteOutput(int length) {
		buffer = ByteBuffer.allocate(length);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
	}
	
	// maybe & 0xFF? o_O
	public void putByte(int index, byte value) {
		buffer.put(index, value);
	}
	
	public void putShort(int index, short value) {
		buffer.putShort(index, value);
	}
	
	public void putInt(int index, int value) {
		buffer.putInt(index, value);
	}
	
	public void putLong(int index, long value) {
		buffer.putLong(index, value);
	}
	
	public void putBytes(int index, byte[] bytes) {
		for (int i = 0; i < bytes.length; i++) {
			putByte(index + i, bytes[i]);
		}
	}
	
	public void putInts(int index, int[] bytes) {
		for (int i = 0; i < bytes.length; i++) {
			putInt(index + 4 * i, bytes[i]);
		}
	}
	
	public void putLongs(int index, long[] bytes) {
		for (int i = 0; i < bytes.length; i++) {
			putLong(index + 8 * i, bytes[i]);
		}
	}
	
	public void putString(int index, String s) {
		if (s == null) {
			return;
		}
		
		for (int i = 0; i < s.length(); i++) {
			putByte(index + i, (byte) s.charAt(i));
		}
	}
	
	public byte[] toArray() {
		return buffer.array();
	}
}
