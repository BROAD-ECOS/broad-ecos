package br.ufjf.nenc.lp.core.util;


public final class CastUtil {

    private CastUtil(){};

    @SuppressWarnings("unchecked")
    public static <T, S extends T> S cast(T obj, Class<S> typeToCast) {

        S objCast = (S) obj;

        return objCast;
    }
}
