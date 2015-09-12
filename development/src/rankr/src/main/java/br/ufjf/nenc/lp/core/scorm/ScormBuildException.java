package br.ufjf.nenc.lp.core.scorm;

public class ScormBuildException extends RuntimeException {
    public ScormBuildException() {
    }

    public ScormBuildException(String message) {
        super(message);
    }

    public ScormBuildException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScormBuildException(Throwable cause) {
        super(cause);
    }

    public ScormBuildException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
