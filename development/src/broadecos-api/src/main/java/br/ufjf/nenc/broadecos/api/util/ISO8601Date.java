package br.ufjf.nenc.broadecos.api.util;

import com.google.common.base.Preconditions;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;

import static br.ufjf.nenc.broadecos.api.util.PreconditionPredicates.isNotNull;
import static javax.xml.bind.DatatypeConverter.parseDateTime;
import static javax.xml.bind.DatatypeConverter.printDateTime;

public class ISO8601Date {

    private final String value;

    private static String format(Date value) {
        return ISO8601DateParser.toString(value);
    }

    private static Date parse(String value) {
        try {
            return ISO8601DateParser.parse(value);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public ISO8601Date(String value) {
        Preconditions.checkArgument(isNotNull(value));
        parse(value);
        this.value = value;
    }

    public ISO8601Date(Date value) {
        Preconditions.checkArgument(isNotNull(value));
        this.value = format(value);
    }

    public ISO8601Date(LocalDateTime value) {
        Preconditions.checkArgument(isNotNull(value));
        this.value = format((Date.from(value.toInstant(ZoneOffset.UTC))));
    }


    public Date asDate(){
        return parse(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
