package br.ufjf.nenc.broadecos.api.exception;

public class BroadEcosApiException extends RuntimeException {

    public BroadEcosApiException() {
    }

    public BroadEcosApiException(String message) {
        super(message);
    }

    public BroadEcosApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public BroadEcosApiException(Throwable cause) {
        super(cause);
    }

    public BroadEcosApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
