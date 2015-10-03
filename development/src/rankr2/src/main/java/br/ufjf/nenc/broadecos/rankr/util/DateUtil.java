package br.ufjf.nenc.broadecos.rankr.util;


import com.google.common.base.Preconditions;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.LocalDate;
import java.util.Date;

public final class DateUtil {

    private DateUtil(){};

    public static LocalDateTime from(Date date) {
        Preconditions.checkArgument(date != null);
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
