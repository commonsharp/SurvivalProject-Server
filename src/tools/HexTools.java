package tools;


public class HexTools {
	public static void printHexArray(byte[] arr, boolean isAscii) {
		printHexArray(arr, 0, isAscii);
	}
	
	public static byte[] duplicateArray(byte[] arr) {
		byte[] arr2 = new byte[arr.length];
		
		for (int i = 0; i < arr.length; i++) {
			arr2[i] = arr[i];
		}
		
		return arr2;
	}
	
	public static void printHexArray(byte[] arr, int offset, boolean isAscii) {
		for (int i = offset; i < arr.length; i++) {
			if ((i - offset) > 0 && (i - offset) % 32 == 0) {
				System.out.println();
			}
			
			if (isAscii) {
				System.out.print((char)arr[i] + " ");
			}
			else {
				System.out.print(byteToHexString(arr[i]) + " ");
			}
		}
		
		System.out.println();
	}
	
	public static String integerToHexString(int i) {
		String result = Integer.toHexString(i).toUpperCase();
		return result.length() == 1 ? "0" + result : result; // to change
	}
	
	public static String byteToHexString(byte b) {
		String result = Integer.toHexString(b & 0xFF).toUpperCase();
		return result.length() == 1 ? "0" + result : result;
	}
	
	public static int getIntegerInByteArrayBigEndian(byte[] arr, int offset) {
		int byte0 = arr[offset] & 0xFF;
		int byte1 = arr[offset + 1] & 0xFF;
		int byte2 = arr[offset + 2] & 0xFF;
		int byte3 = arr[offset + 3] & 0xFF;
		
		return (byte0 << 24) + (byte1 << 16) + (byte2 << 8) + byte3;
	}
	
	public static int getIntegerInByteArray(byte[] arr, int offset) {
		int byte0 = arr[offset] & 0xFF;
		int byte1 = arr[offset + 1] & 0xFF;
		int byte2 = arr[offset + 2] & 0xFF;
		int byte3 = arr[offset + 3] & 0xFF;
		
		return (byte3 << 24) + (byte2 << 16) + (byte1 << 8) + byte0;
	}
	
	public static void putIntegerInByteArray(byte[] arr, int offset, int number) {
		byte[] intArray = intToByteArray(number);
		for (int i = 0; i < 4; i++) {
			arr[offset + i] = intArray[i];
		}
	}
	
	public static final byte[] intToByteArray(int value) {
//		return new byte[] {
//	            (byte)(value >> 24),
//	            (byte)(value >> 16),
//	            (byte)(value >> 8),
//	            (byte)(value >> 0)};
	    return new byte[] {
	            (byte)(value >> 0),
	            (byte)(value >> 8),
	            (byte)(value >> 16),
	            (byte)(value >> 24)};
	}
}
