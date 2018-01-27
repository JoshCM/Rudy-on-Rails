package exceptions;


public class NotChangeableGameObject extends RuntimeException {
    private static final long serialVersionUID = -2967984911717078734L;

    public NotChangeableGameObject(String message) {
        super(message);
    }
}
