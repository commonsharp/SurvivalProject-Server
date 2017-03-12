package tools;


public class HexTools {
	public static void printHexArray(byte[] arr, boolean isAscii) {
		for (int i = 0; i < arr.length; i++) {
			if (i > 0 && i % 32 == 0) {
				System.out.println();
			}
			
			if (isAscii) {
				System.out.print((char)arr[i] + " ");
			}
			else {
				System.out.print(byteToHexString(arr[i]) + " ");
			}
		}
	}
	
	public static String byteToHexString(byte b) {
		String result = Integer.toHexString(b & 0xFF).toUpperCase();
		return result.length() == 1 ? "0" + result : result;
	}
	
	public static int getIntegerInByteArray(byte[] arr, int offset) {
		int byte0 = arr[offset];
		int byte1 = arr[offset + 1];
		int byte2 = arr[offset + 2];
		int byte3 = arr[offset + 3];
		
		return (byte3 << 24) + (byte2 << 16) + (byte1 << 8) + byte0;
	}
	
	public static void putIntegerInByteArray(byte[] arr, int offset, int number) {
		for (int i = 0; i < 4; i++) {
			arr[offset + i] = (byte) (number % 256);
			number /= 256;
		}
	}
	
	public static byte[] intToByteArray(int number) {
		byte[] bytes = new byte[4];
		
		for (int i = 0; i < 4; i++) {
			bytes[i] = (byte) (number % 256);
			number /= 256;
		}
		
		return bytes;
	}
}
