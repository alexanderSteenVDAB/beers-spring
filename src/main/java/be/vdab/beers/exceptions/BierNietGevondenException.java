package be.vdab.beers.exceptions;

public class BierNietGevondenException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BierNietGevondenException(String message) {
        super(message);
    }

    public BierNietGevondenException(String message, Exception exception) {
        super(message, exception);
    }
}
