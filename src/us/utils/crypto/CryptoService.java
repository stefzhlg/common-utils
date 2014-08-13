/*
 * Copyright 2012 Hongdian, Inc. All rights reserved.
 */

package us.utils.crypto;

import com.sun.syndication.io.impl.Base64;

public class CryptoService {
	private static final byte salt[] = "sadwekldncxmaysujJ？、sxdbnvczdxsdhjaskv;l'[];**&8127312".getBytes();
	private static final int HASH_COUNT = 2;
	private static DataHash md5 = new DataHash("MD5");

	public static String hash(String str1) {
		if (str1 == null)
			return null;
		return md5.hash(str1, salt, HASH_COUNT);
	}

	public static String encode64(String str1) {
		if (str1 == null)
			return null;
		return Base64.encode(str1);
	}

	public static String decode64(String str1) {
		if (str1 == null)
			return null;
		return Base64.decode(str1);
	}

}
