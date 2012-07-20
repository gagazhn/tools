package gagazhn.MD5;

import java.security.MessageDigest;

public class MD5Tools {
	private static MessageDigest md;
	
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	
	public static byte[] getMD5(String string) {
		if (md == null) {
			try {
				md = MessageDigest.getInstance("MD5");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		md.update(string.getBytes());
		return md.digest();
	}
	
	public static String MD5ToString(byte[] md5) {
//		// MD5的String表示方法
//		int i;
//		StringBuffer sb = new StringBuffer("");
		//		for (int offset = 0; offset < md5.length; offset++) {
		//			i = md5[offset];
		//			if (i < 0) i += 256;
		//			if (i < 16)
		//				sb.append("0");
		//			sb.append(Integer.toHexString(i));
		//		}
		//		
		//		return sb.toString();
		
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < md5.length; i++) {
			resultSb.append(byteToHexString(md5[i]));
		}
		
		return resultSb.toString();
	}
	
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
	
	public static String getMD5String(String string) {
		return MD5Tools.MD5ToString(MD5Tools.getMD5(string));
	}
			
	public static byte[] StringToMD5(String md5String) {
		byte[] returnBytes = new byte[16];
		
		for (int i = 0, j = 0; i < 32; i += 2, j++) {
			returnBytes[j] = (byte)Integer.parseInt(md5String.substring(i, i + 2), 16);
		}
		
		return returnBytes;
	}
	
	public static int getMD5Index(byte[] md5) {
		int index = 0;
		for (int i = 0; i <  1; i++) {
			index += ((int) (md5[i] & 0xFF)) << ((0 - i) * 8);
		}
		
		return index;
	}
	
	public static void main(String[] args) {
		System.out.println(MD5Tools.getMD5String(""));
	
	}
}
