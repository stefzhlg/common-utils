package us.utils.crypto;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class BaseCode {

	public static final String base64_encode(String str) {
		return new sun.misc.BASE64Encoder().encode(str.getBytes());
	}

	public static final String base64_encode(byte[] str) {
		return new sun.misc.BASE64Encoder().encode(str);
	}

	public static final byte[] base64_decodeTobyte(String str) {
		try {
			return new sun.misc.BASE64Decoder().decodeBuffer(str);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static final String base64_decode(String str) {
		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
		if (str == null)
			return null;
		try {
			return new String(decoder.decodeBuffer(str));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static final String URLEncode(String s, String charaset) throws UnsupportedEncodingException {
		return URLEncoder.encode(s, charaset);
	}

	public static final String URLDecode(String s, String charaset) throws UnsupportedEncodingException {
		return URLDecoder.decode(s, charaset);
	}
}