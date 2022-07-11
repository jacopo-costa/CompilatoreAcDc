package exception;

public class ScannerException extends Exception {

    public ScannerException() {
        super();
    }

    public ScannerException(String message) {
        super(message);
    }

    public ScannerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScannerException(Throwable cause) {
        super(cause);
    }

}
