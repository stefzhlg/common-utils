package us.utils.exception;

public class PersistentException extends RuntimeException {

	private static final long serialVersionUID = -324680339772385534L;

	public PersistentException() {
	}

	public PersistentException(String message) {
		super(message);
	}

	public PersistentException(Throwable cause) {
		super(cause);
	}

	public PersistentException(String message, Throwable cause) {
		super(message, cause);

	}

}
