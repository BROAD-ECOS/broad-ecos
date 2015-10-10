package br.ufjf.nenc.broadecos.platform.api;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Resource {

    @XmlSchemaType(name = "Method")
    private Method method;

    private String path;

    @XmlSchemaType(name = "ResourceType")
    private ResourceType type;

    @XmlAttribute(name="isarray", required = false)
    private String query;

    @XmlAttribute(name="isarray", required = false)
    private Boolean isArray;

    @XmlElementWrapper( name="required-scopes" )
    @XmlElement( name="required-scope" )
    private List<String> requiredScope;

    @XmlElementWrapper( name="params" )
    @XmlElement( name="param" )
    private List<Param> params;

}
