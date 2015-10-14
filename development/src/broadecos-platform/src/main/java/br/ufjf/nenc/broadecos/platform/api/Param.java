package br.ufjf.nenc.broadecos.platform.api;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Param {
    private String name;

    private Boolean required;

    @XmlElement( name="default" )
    private String defaultValue;
}
