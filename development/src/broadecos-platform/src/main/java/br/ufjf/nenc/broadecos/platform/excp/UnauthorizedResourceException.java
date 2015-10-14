package br.ufjf.nenc.broadecos.platform.excp;

public class UnauthorizedResourceException extends PlatformException {

    public UnauthorizedResourceException() {
    }

    public UnauthorizedResourceException(String message) {
        super(message);
    }

    public UnauthorizedResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedResourceException(Throwable cause) {
        super(cause);
    }

    public UnauthorizedResourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}