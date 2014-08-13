package us.utils.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -324680339772385598L;

	public BusinessException() {
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);

	}

}
