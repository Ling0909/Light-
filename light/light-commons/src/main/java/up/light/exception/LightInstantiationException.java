package up.light.exception;

public class LightInstantiationException extends Exception {
	private static final long serialVersionUID = -4709984926100943200L;

	public LightInstantiationException() {
		super();
	}

	public LightInstantiationException(String message, Throwable cause) {
		super(message, cause);
	}

	public LightInstantiationException(String message) {
		super(message);
	}

	public LightInstantiationException(Throwable cause) {
		super(cause);
	}

}
