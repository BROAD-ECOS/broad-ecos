package br.ufjf.nenc.broadecos.api.model;

import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonRootName;

import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

@Data
@JsonRootName("extensions")
public class RequestedExtensions {

    private final String id;
    private final Set<RequestedScope> scopes;
    private final String reason;

    public RequestedExtensions(String id, Set<RequestedScope> scopes, String reason) {
        checkArgument(id!=null);
        checkArgument(scopes!=null);
        checkArgument(reason!=null);
        this.id = id;
        this.scopes = scopes;
        this.reason = reason;
    }
}
