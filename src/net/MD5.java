package net;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import tools.HexTools;

public class MD5 {
	public static int getDigest(byte[] messageBytes) {
		try {
			byte[] buff;
			
			// MUST PUT 0 IN THE CHECKSUM OR ELSE THE MD5 WILL BE COMPLETELY DIFFERENT
			HexTools.putIntegerInByteArray(messageBytes, 12, 0);
			
			MessageDigest md;
		
			md = MessageDigest.getInstance("MD5");
		
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
}
