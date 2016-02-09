package br.ufjf.nenc.broadecos.platform.excp;

import javaslang.control.Try;

public class PlatformException extends RuntimeException {

    public static <T> T reThrow(Throwable t) {
        throw new PlatformException(t);
    }

    public PlatformException() {
    }

    public PlatformException(String message) {
        super(message);
    }

    public PlatformException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlatformException(Throwable cause) {
        super(cause);
    }

    public PlatformException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
