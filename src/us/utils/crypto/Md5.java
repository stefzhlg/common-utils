/**  
 * @Title: Md5.java
 * @Package com.hongdian.cardprize.util
 * @Description: TODO(用一句话描述该文件做什么)
 * @author stef zhao
 * @date 2013-5-20 下午02:59:38
 * @version V1.0  
 */
package us.utils.crypto;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {

	/**
	 * 获取字符串的Md5码
	 * 
	 * @param str
	 *            　文件路径
	 * @return
	 */
	public static String getMd5ByString(String str) {

		String inStr = str;
		java.security.MessageDigest md = null;
		String out = null;

		try {
			md = java.security.MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(inStr.getBytes());
			out = byte2hex(digest);
		} catch (NoSuchAlgorithmException e) {
			// e.printStackTrace();
			return "";
		}
		// System.out.println(out);
		return out;
	}

	/**
	 * 获取文件的Md5码
	 * 
	 * @param filePath
	 *            　文件路径
	 * @return
	 */
	public static String getMd5ByFile(String filePath) {

		try {

			InputStream fis;
			fis = new FileInputStream(filePath);
			byte[] buffer = new byte[1024];

			MessageDigest md5 = MessageDigest.getInstance("MD5");
			int numRead = 0;
			while ((numRead = fis.read(buffer)) > 0) {
				md5.update(buffer, 0, numRead);
			}
			fis.close();
			return byte2hex(md5.digest());
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 整理成32位大写的MD5
	 * 
	 * @param b
	 * @return
	 */
	private static String byte2hex(byte[] b) {

		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

	public static void main(String args[]) {
		System.out.println(Md5.getMd5ByString("1111111"));
	}
}
