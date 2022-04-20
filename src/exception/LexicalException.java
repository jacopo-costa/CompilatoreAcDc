package exception;

public class LexicalException extends Exception{

    public LexicalException() {
        super();
    }

    public LexicalException(String message) {
        super(message);
    }

    public LexicalException(String message, Throwable cause) {
        super(message, cause);
    }

    public LexicalException(Throwable cause) {
        super(cause);
    }

}
