/*
 * Copyright 2012 Hongdian, Inc. All rights reserved.
 */

package us.utils.exception;

/**
 * Exception thrown when attempting to lookup or use a cryptographic algorithm
 * that does not exist in the current JVM environment.
 * 
 * @since 1.0
 */

public class UnknownAlgorithmException extends RuntimeException {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -253105228624092909L;

	public UnknownAlgorithmException(String message) {
		super(message);
	}

	public UnknownAlgorithmException(Throwable cause) {
		super(cause);
	}

	public UnknownAlgorithmException(String message, Throwable cause) {
		super(message, cause);
	}
}
