package exceptions;

public class ISBNAlreadyExistsException  extends Exception {
    public ISBNAlreadyExistsException(String message) {
        super(message);
    }
}