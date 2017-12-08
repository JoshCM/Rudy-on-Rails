package exceptions;

public class InvalidModelOperationException extends RuntimeException {
	private static final long serialVersionUID = 4485002961961737221L;
	
	public InvalidModelOperationException(String message) {
		super(message);
	}
}
