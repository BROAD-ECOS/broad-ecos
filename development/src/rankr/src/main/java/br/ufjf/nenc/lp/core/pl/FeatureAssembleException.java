package br.ufjf.nenc.lp.core.pl;

public class FeatureAssembleException extends RuntimeException {
    public FeatureAssembleException() {
    }

    public FeatureAssembleException(String message) {
        super(message);
    }

    public FeatureAssembleException(String message, Throwable cause) {
        super(message, cause);
    }

    public FeatureAssembleException(Throwable cause) {
        super(cause);
    }

    public FeatureAssembleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
