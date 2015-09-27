package br.ufjf.nenc.broadecos.api.util;

import com.google.common.base.Preconditions;

import java.util.Date;

import static br.ufjf.nenc.broadecos.api.util.PreconditionPredicates.isNotNull;
import static javax.xml.bind.DatatypeConverter.parseDateTime;

public class ISO8601Date {

    private final String value;

    public ISO8601Date(String value) {
        Preconditions.checkArgument(isNotNull(value));
        this.value = value;
    }

    public Date asDate(){
        return parseDateTime(value).getTime();
    }
}
