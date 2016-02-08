package br.ufjf.nenc.broadecos.api.model;

import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonRootName;

import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

@Data
@JsonRootName("scope")
public class RequestedExtensions {

    private final Scope id;
    private final Set<RequestedScope> scopes;
    private final String reason;

    public RequestedExtensions(Scope id, Set<RequestedScope> scopes, String reason) {
        checkArgument(id!=null);
        checkArgument(scopes!=null);
        checkArgument(reason!=null);
        this.id = id;
        this.scopes = scopes;
        this.reason = reason;
    }
}
