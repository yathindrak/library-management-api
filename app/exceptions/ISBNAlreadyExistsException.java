package exceptions;

/**
 * Exception for an invalid ISBN
 */
public class ISBNAlreadyExistsException  extends Exception {
    public ISBNAlreadyExistsException(String message) {
        super(message);
    }
}