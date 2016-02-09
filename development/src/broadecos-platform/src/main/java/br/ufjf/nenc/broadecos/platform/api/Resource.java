package br.ufjf.nenc.broadecos.platform.api;

import br.ufjf.nenc.broadecos.platform.model.MatchedResource;
import com.google.common.collect.ImmutableMap;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Resource {

    @XmlSchemaType(name = "Method")
    private Method method;

    private String path;

    @XmlSchemaType(name = "ResourceType")
    private ResourceType type;

    @XmlElement(name="query", required = false)
    private String query;

    @XmlElement(name="isarray", required = false)
    private Boolean isArray;

    @XmlElementWrapper( name="required-scopes" )
    @XmlElement( name="required-scope" )
    private List<String> requiredScope;

    @XmlElementWrapper( name="parameters" )
    @XmlElement( name="param" )
    private List<Param> params;

    public Optional<MatchedResource> match(String candidate) {
        boolean met = true;
        ImmutableMap.Builder<String, String> urlParams = ImmutableMap.builder();
        String[] candidateParts = candidate.split("/");
        String[] pathParts = path.split("/");

        if (candidateParts.length == pathParts.length) {
            for (int i=0; i < pathParts.length; i++) {
                if (pathParts[i].contains("{")) {
                    String paramName = pathParts[i].replaceAll("\\{", "").replaceAll("}", "");
                    String value = candidateParts[i];
                    urlParams.put(paramName, value);
                } else {
                    if (!candidateParts[i].equals(pathParts[i])) {
                        met = false;
                        break;
                    }
                }
            }
        } else {
            met = false;
        }

        Optional<MatchedResource>  matchedResource;
        if (met) {
            matchedResource = Optional.of(new MatchedResource(this, urlParams.build()));
        } else {
            matchedResource = Optional.empty();
        }

        return matchedResource;
    }
}
