package us.utils.crypto;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class AzDG {
	private static final String PASSWORD_CRYPT_KEY = "pK93Unaq4angUmFwthM1qwTT";

	public static byte[] encrypt(byte txt[], byte key[]) {
		int rand = (new Double(Math.random() * 32000D)).intValue();
		new Md5();
		byte encrypt_key[] = Md5.getMd5ByString((new StringBuilder(String.valueOf(rand))).toString()).toLowerCase().getBytes();
		byte ctr = 0;
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		int i = 0;
		for (int j = 0; i < txt.length; j += 2) {
			ctr = ctr != encrypt_key.length ? ctr : 0;
			byteOut.write(encrypt_key[ctr]);
			byteOut.write(txt[i] ^ encrypt_key[ctr++]);
			i++;
		}

		return getBASE64(new String(encodeKey(byteOut.toByteArray(), key))).getBytes();
	}

	public static String encrypt(String txt, String key) {
		return new String(encrypt(txt.getBytes(), key.getBytes()));
	}

	public static String encrypt(String txt, String key, String encoding) {
		String str = null;
		try {
			str = new String(encrypt(txt.getBytes(encoding), key.getBytes()));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	public static String getBASE64(String s) {
		if (s == null)
			return null;
		else
			return (new BASE64Encoder()).encode(s.getBytes());
	}

	public static String getFromBASE64(String s) {
		try {
			BASE64Decoder decoder;
			if (s == null)
				return null;
			decoder = new BASE64Decoder();
			byte b[] = decoder.decodeBuffer(s);
			return new String(b);
		} catch (Exception e) {
			return null;
		}
	}

	public static byte[] decrypt(byte txt[], byte key[]) {
		txt = encodeKey(getFromBASE64(new String(txt)).getBytes(), key);
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		for (int i = 0; i < txt.length; i++) {
			byte md5 = txt[i];
			byteOut.write(txt[++i] ^ md5);
		}

		return byteOut.toByteArray();
	}

	public static String decrypt(String txt, String key) {
		return new String(decrypt(txt.getBytes(), key.getBytes()));
	}

	public static String decrypt(String txt) {
		return new String(decrypt(txt.getBytes(), PASSWORD_CRYPT_KEY.getBytes()));
	}

	public static String encrypt(String txt) {
		return new String(encrypt(txt.getBytes(), PASSWORD_CRYPT_KEY.getBytes()));
	}

	public static byte[] encodeKey(byte txt[], byte encrypt_key[]) {
		new Md5();
		encrypt_key = Md5.getMd5ByString(new String(encrypt_key)).toLowerCase().getBytes();
		byte ctr = 0;
		byte tmp[] = new byte[txt.length];
		for (int i = 0; i < txt.length; i++) {
			ctr = ctr != encrypt_key.length ? ctr : 0;
			tmp[i] = (byte) (txt[i] ^ encrypt_key[ctr++]);
		}

		return tmp;
	}
}
