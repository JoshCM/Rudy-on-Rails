/*
 * Copyright (c) 2018. Florian Treder
 */

package exceptions;

public class InvalidStringForEnumType extends RuntimeException {
    public InvalidStringForEnumType(String message) {
        super(message);
    }
}
