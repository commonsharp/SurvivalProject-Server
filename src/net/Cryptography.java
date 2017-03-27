package net;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import tools.HexTools;

public class Cryptography {
	public static int getDigest(byte[] messageBytes) {
		try {
			byte[] buff;
			
			// MUST PUT 0 IN THE CHECKSUM OR ELSE THE MD5 WILL BE COMPLETELY DIFFERENT
			HexTools.putIntegerInByteArray(messageBytes, 0xC, 0);
		
			MessageDigest md = MessageDigest.getInstance("MD5");
		
			md.update(messageBytes);
			buff = md.digest();
			
			int int0 = HexTools.getIntegerInByteArray(buff, 0x0);
			int int1 = HexTools.getIntegerInByteArray(buff, 0x4);
			int int2 = HexTools.getIntegerInByteArray(buff, 0x8);
			int int3 = HexTools.getIntegerInByteArray(buff, 0xC);

			return int0 ^ int1 ^ int2 ^ int3;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// ERROR HERE
		return 0;
	}
	
	public static void encryptMessage(int encryptionVersion, byte[] buffer) {
		int c;
		
		for (int i = 4; i < buffer.length; i++) {
			c = buffer[i] & 0xFF;
			
			if (encryptionVersion == 2) {
				c = (byte)(c ^ 0x74);
				c = (byte)(c - (i - 4));
			}
			
			c = (~c) & 0xFF;
			buffer[i] = (byte) (((c >> 5) & 0xFF) | ((c << 3) & 0xFF));
		}
	}
	
	public static void decryptMessage(int encryptionVersion, byte[] buffer) {
		for (int i = 4; i < buffer.length; i++) {
			if (encryptionVersion == 2) {
				buffer[i] = (byte)(buffer[i] ^ 0x3C);
				buffer[i] = (byte)(buffer[i] - (i - 4));
			}
			
			buffer[i] = (byte) ~(((buffer[i] & 0xFF) >> 3) | ((buffer[i] & 0xFF) << 5));
		}
	}
	
	public static int getNewState(int oldState) {
		if (oldState == -1)
			return 0;
		
		oldState = (~oldState + 0x14fb) * 0x1f;
		return Math.abs((oldState >> 16) ^ oldState);
	}
}
