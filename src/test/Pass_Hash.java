package test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Pass_Hash {
	public static void main(String[] args) throws Exception {
		String data = "123456"; //password
		String algorithm = "MD5";
		//byte[] salt = createSalt();
		System.out.println("MD5 with Salt hash : " + generatehash(data, algorithm/*, salt*/));
}

	/*private static byte[] createSalt() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[20];
		SecureRandom random = new SecureRandom();
		random.nextBytes(bytes);
		return bytes;
	}*/

	private static String generatehash(String data, String algorithm/*, byte[] salt*/)  throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		MessageDigest md = MessageDigest.getInstance(algorithm);
		md.reset();
		//md.update(salt);
		byte[] hash = md.digest(data.getBytes());
		return bytesToStringHex(hash);
	}
	
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

	public static String bytesToStringHex(byte[] bytes) {
		// TODO Auto-generated method stub
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars [j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}
	}

