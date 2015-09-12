package br.ufjf.nenc.lp.core.pl;

public class FeatureTypeNotFoundException extends Exception {
    public FeatureTypeNotFoundException() {
    }

    public FeatureTypeNotFoundException(String message) {
        super(message);
    }

    public FeatureTypeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FeatureTypeNotFoundException(Throwable cause) {
        super(cause);
    }

    public FeatureTypeNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
