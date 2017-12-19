package exceptions;

public class NotRemoveableException extends RuntimeException{
	private static final long serialVersionUID = 5689169500871986419L;

	public NotRemoveableException(String message) {
		super(message);
	}
}
