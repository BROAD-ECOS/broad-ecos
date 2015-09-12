package br.ufjf.nenc.lp.core.util;

import com.google.common.base.Preconditions;

public final class ArgumentUtil {

    public static <T> T or(T value, T defaultValue) {
        Preconditions.checkArgument(defaultValue!=null);

        return value != null ? value : defaultValue;
    }

}
