package dev.demmage.simpleyamlreader.exception;

public class YamlResourceInputStreamReaderException extends RuntimeException {

    public YamlResourceInputStreamReaderException() {
        super();
    }

    public YamlResourceInputStreamReaderException(String message) {
        super(message);
    }

    public YamlResourceInputStreamReaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public YamlResourceInputStreamReaderException(Throwable cause) {
        super(cause);
    }
}
