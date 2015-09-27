package br.ufjf.nenc.broadecos.api.util;

public final class PreconditionPredicates {

    private PreconditionPredicates(){};

    public static boolean isNotNull(Object o) {
        return o != null;
    }

    public static boolean isNull(Object o) {
        return o == null;
    }

}
