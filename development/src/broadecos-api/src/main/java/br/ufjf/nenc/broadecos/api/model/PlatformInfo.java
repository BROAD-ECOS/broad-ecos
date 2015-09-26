package br.ufjf.nenc.broadecos.api.model;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
public class PlatformInfo {
    private String url;
    private String name;
    private String logo;

    @XmlElement(name="moreinfo")
    @JsonProperty("moreinfo")
    private String moreInfo;
}
