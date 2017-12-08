package exceptions;

/**
 * Base-Klasse für alle Exceptions, die von Model-Operationen wie erzeugen, ändern oder löschen geworfen werden
 */
public class InvalidModelOperationException extends RuntimeException {
	private static final long serialVersionUID = 4485002961961737221L;
	
	public InvalidModelOperationException(String message) {
		super(message);
	}
}
