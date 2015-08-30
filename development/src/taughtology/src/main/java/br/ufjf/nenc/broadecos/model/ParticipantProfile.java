package br.ufjf.nenc.broadecos.model;

import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Data
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
public class ParticipantProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @XmlElement(name="firstname")
    private String firstName;

    @XmlElement(name="lastname")
    private String lastName;

    private String picture;

    private String locale;

    private String platform;

}
