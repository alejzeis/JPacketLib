package io.github.jython234.jpacketlib;

/**
 * Exception for parser related errors.
 */
public class ParseException extends RuntimeException {

    public ParseException(String s) {
        super(s);
    }

    public ParseException(Exception e) {
        super(e);
    }
}
