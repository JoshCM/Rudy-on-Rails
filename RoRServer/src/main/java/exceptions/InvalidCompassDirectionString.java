/*
 * Copyright (c) 2018. Florian Treder
 */

package exceptions;

public class InvalidCompassDirectionString extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidCompassDirectionString(String message) {
        super(message);
    }
}
