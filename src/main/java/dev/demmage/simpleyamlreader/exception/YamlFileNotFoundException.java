package dev.demmage.simpleyamlreader.exception;

public class YamlFileNotFoundException extends RuntimeException {

    public YamlFileNotFoundException() {
        super();
    }

    public YamlFileNotFoundException(String message) {
        super(message);
    }

    public YamlFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public YamlFileNotFoundException(Throwable cause) {
        super(cause);
    }
}
