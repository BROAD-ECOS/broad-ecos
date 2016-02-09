package br.ufjf.nenc.broadecos.platform.api;

import lombok.Getter;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


@XmlEnum
@XmlType(name = "Method")
@Getter
public enum Method {
    @XmlEnumValue("GET")
    GET("GET"),
    @XmlEnumValue("POST")
    POST("POST");

    private final String method;

    Method(String method) {
        this.method = method;
    }
}
