package mobile.zxjt.testng;

public class StopException extends RuntimeException {
	private static final long serialVersionUID = -3146142583892551437L;

	public StopException() {
		super();
	}

	public StopException(String message, Throwable cause) {
		super(message, cause);
	}

	public StopException(String message) {
		super(message);
	}

	public StopException(Throwable cause) {
		super(cause);
	}

}
