package exceptions;

public class NotRotateableException extends RuntimeException{
	private static final long serialVersionUID = -5071886255036451694L;

	public NotRotateableException(String message) {
		super(message);
	}
}
