/*
 * Copyright 2012 Hongdian, Inc. All rights reserved.
 */
package us.utils.exception;

/**
 * Root exception related to issues during encoding or decoding.
 * 
 * @since 0.9
 */
public class CodecException extends RuntimeException {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 593456587450049661L;

	/**
	 * Creates a new <code>CodecException</code>.
	 */
	public CodecException() {
		super();
	}

	/**
	 * Creates a new <code>CodecException</code>.
	 * 
	 * @param message
	 *            the reason for the exception.
	 */
	public CodecException(String message) {
		super(message);
	}

	/**
	 * Creates a new <code>CodecException</code>.
	 * 
	 * @param cause
	 *            the underlying cause of the exception.
	 */
	public CodecException(Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a new <code>CodecException</code>.
	 * 
	 * @param message
	 *            the reason for the exception.
	 * @param cause
	 *            the underlying cause of the exception.
	 */
	public CodecException(String message, Throwable cause) {
		super(message, cause);
	}
}
