package br.ufjf.nenc.broadecos.platform.api;

import lombok.Getter;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlEnum
@XmlType(name = "ResourceType")
@Getter
public enum ResourceType {
    @XmlEnumValue("platform_query")
    PLATFORM_QUERY("platform_query"),

    @XmlEnumValue("lrs_statement_post")
    EXPERIENCE_POST("lrs_statement_post"),

    @XmlEnumValue("lrs_statement_get")
    EXPERIENCE_GET("lrs_statement_get"),

    @XmlEnumValue("authorize")
    AUTHORIZATION("authorize"),

    @XmlEnumValue("token")
    TOKEN("token");

    private final String type;

    ResourceType(String type) {
        this.type = type;
    }
}
