package exceptions;

/**
 * Wird zurückgegeben, wenn ein Request nicht umgesetzt werden kann.
 */
public class NotHandlebarException extends Exception {
    NotHandlebarException(String message) {
        super(message);
    }
}
