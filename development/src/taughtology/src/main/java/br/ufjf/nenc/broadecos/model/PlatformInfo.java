package br.ufjf.nenc.broadecos.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
public class PlatformInfo {
    private String url;
    private String name;
    private String logo;
    private String moreInfo;
}
