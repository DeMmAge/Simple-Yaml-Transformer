package dev.demmage.simpleyamlreader.exception;

public class YamlFileWriteException extends RuntimeException {

    public YamlFileWriteException() {
        super();
    }

    public YamlFileWriteException(String message) {
        super(message);
    }

    public YamlFileWriteException(String message, Throwable cause) {
        super(message, cause);
    }

    public YamlFileWriteException(Throwable cause) {
        super(cause);
    }
}
