package us.utils.exception;

public class ConfigException extends RuntimeException {

	private static final long serialVersionUID = -4324037699977238554L;

	public ConfigException() {
	}

	public ConfigException(String message) {
		super(message);
	}

	public ConfigException(Throwable cause) {
		super(cause);
	}

	public ConfigException(String message, Throwable cause) {
		super(message, cause);

	}

}
