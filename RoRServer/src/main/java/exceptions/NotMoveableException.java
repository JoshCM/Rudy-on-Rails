package exceptions;

public class NotMoveableException extends RuntimeException{
	private static final long serialVersionUID = 6900638867524048154L;
	
	public NotMoveableException(String message) {
		super(message);
	}
}
