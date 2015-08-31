package br.ufjf.nenc.broadecos.model;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Data
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Course {
    private String id;

    @XmlElement(name="fullname")
    @JsonProperty("fullname")
    private String fullName;

    @XmlElement(name="shortname")
    @JsonProperty("shortname")
    private String shortName;

    private String summary;

    @XmlElement(name="startdate")
    @JsonProperty("startdate")
    private Date startDate;

    private String language;
}
