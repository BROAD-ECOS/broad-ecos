package br.ufjf.nenc.broadecos.model;

import lombok.Data;

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
    private String fullName;
    @XmlElement(name="shortname")
    private String shortName;
    private String summary;
    private Date startDate;
    private String language;
}
