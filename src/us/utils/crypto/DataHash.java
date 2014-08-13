/*
 * Copyright 2012 Hongdian, Inc. All rights reserved.
 */

package us.utils.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import us.utils.exception.UnknownAlgorithmException;


public class DataHash {
	private static final int DEFAULT_ITERATIONS = 1;
	private final String algorithmName;

	public DataHash(String algorithmName) {
		this.algorithmName = algorithmName;
	}

	public String getAlgorithmName() {
		return this.algorithmName;
	}

	protected MessageDigest getDigest(String algorithmName) throws UnknownAlgorithmException {
		try {
			return MessageDigest.getInstance(algorithmName);
		} catch (NoSuchAlgorithmException e) {
			String msg = "No native '" + algorithmName + "' MessageDigest instance available on the current JVM.";
			throw new UnknownAlgorithmException(msg, e);
		}
	}

	protected byte[] hash(byte[] bytes, byte[] salt, int hashIterations) throws UnknownAlgorithmException {
		MessageDigest digest = getDigest(getAlgorithmName());
		if (salt != null) {
			digest.reset();
			digest.update(salt);
		}
		byte[] hashed = digest.digest(bytes);
		int iterations = hashIterations - DEFAULT_ITERATIONS; // already hashed
																// once above
		// iterate remaining number:
		for (int i = 0; i < iterations; i++) {
			digest.reset();
			hashed = digest.digest(hashed);
		}
		return hashed;
	}

	public String hash(String mess, int hastCount) {
		byte[] rs = hash(mess.getBytes(), null, hastCount);
		return new String(rs);
	}

	public String hash(String mess, byte[] salt, int hastCount) {
		byte[] rs = hash(mess.getBytes(), salt, hastCount);
		return Hex.encodeToString(rs);
	}

}
