package br.ufjf.nenc.lp.core.util;

public final class ArrayUtil {

    public static <T> T firstOrNull(T[] array) {

        return firstOrDefault(array, null);
    }

    public static <T> T firstOrDefault(T[] array, T defaultValue) {

        T first = defaultValue;

        if (array != null && array.length > 0) {
            first = array[0];
        }

        return first;
    }

}
