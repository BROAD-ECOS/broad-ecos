package br.ufjf.nenc.lp.core.pl;


public class ProductLineException extends Exception {

    public ProductLineException() {
    }

    public ProductLineException(String message) {
        super(message);
    }

    public ProductLineException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductLineException(Throwable cause) {
        super(cause);
    }

    public ProductLineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
