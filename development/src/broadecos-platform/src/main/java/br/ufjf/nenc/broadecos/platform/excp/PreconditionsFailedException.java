package br.ufjf.nenc.broadecos.platform.excp;

public class PreconditionsFailedException extends PlatformException {
    public PreconditionsFailedException() {
    }

    public PreconditionsFailedException(String message) {
        super(message);
    }

    public PreconditionsFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public PreconditionsFailedException(Throwable cause) {
        super(cause);
    }

    public PreconditionsFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
