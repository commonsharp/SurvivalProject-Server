package tools;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ExtendedByteBuffer {
	protected ByteBuffer buffer;
	
	public ExtendedByteBuffer(int length) {
		buffer = ByteBuffer.allocate(length);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
	}
	
	public ExtendedByteBuffer(byte[] messageBytes) {
		buffer = ByteBuffer.wrap(messageBytes);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
	}
	
	public byte getByte(int index) {
		return buffer.get(index);
	}
	
	public void putByte(int index, byte value) {
		buffer.put(index, value);
	}
	
	public short getShort(int index) {
		return buffer.getShort(index);
	}
	
	public void putShort(int index, short value) {
		buffer.putShort(index, value);
	}
	
	public int getInt(int index) {
		return buffer.getInt(index);
	}
	
	public void putInt(int index, int value) {
		buffer.putInt(index, value);
	}
	
	public long getLong(int index) {
		return buffer.getLong(index);
	}
	
	public void putLong(int index, long value) {
		buffer.putLong(index, value);
	}
	
	public byte[] getBytes(int index, int length) {
		byte[] bytes = new byte[length];
		
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = getByte(index + i);
		}
		
		return bytes;
	}
	
	public void putBytes(int index, byte[] bytes) {
		for (int i = 0; i < bytes.length; i++) {
			putByte(index + i, bytes[i]);
		}
	}
	
	public short[] getShorts(int index, int length) {
		short[] shorts = new short[length];
		
		for (int i = 0; i < shorts.length; i++) {
			shorts[i] = getShort(index + 2 * i);
		}
		
		return shorts;
	}
	
	public void putShorts(int index, short[] bytes) {
		for (int i = 0; i < bytes.length; i++) {
			putShort(index + 2 * i, bytes[i]);
		}
	}
	
	public int[] getInts(int index, int length) {
		int[] ints = new int[length];
		
		for (int i = 0; i < ints.length; i++) {
			ints[i] = getInt(index + 4 * i);
		}
		
		return ints;
	}
	
	public void putInts(int index, int[] bytes) {
		for (int i = 0; i < bytes.length; i++) {
			putInt(index + 4 * i, bytes[i]);
		}
	}
	
	public long[] getLongs(int index, int length) {
		long[] longs = new long[length];
		
		for (int i = 0; i < longs.length; i++) {
			longs[i] = getInt(index + 8 * i);
		}
		
		return longs;
	}
	
	public void putLongs(int index, long[] bytes) {
		for (int i = 0; i < bytes.length; i++) {
			putLong(index + 8 * i, bytes[i]);
		}
	}
	
	public String getString(int index) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		int i = 0;
		byte b;
		
		while ((b = (byte) getByte(index + i)) != 0) {
			baos.write(b);
			i++;
		}
		
		return new String(baos.toByteArray());
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
