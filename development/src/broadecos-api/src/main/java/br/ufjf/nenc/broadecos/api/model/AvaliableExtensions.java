package br.ufjf.nenc.broadecos.api.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.map.annotate.JsonRootName;

import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

@Data
@JsonRootName("extensions")
@NoArgsConstructor
public class AvaliableExtensions {

    private  String id;
    private  Set<String> scopes;
    private  String reason;
    private  String namespace;

    public AvaliableExtensions(String id, Set<String> scopes, String reason, String namespace) {
        checkArgument(id!=null);
        checkArgument(scopes!=null);
        checkArgument(reason!=null);
        checkArgument(namespace!=null);
        this.id = id;
        this.scopes = scopes;
        this.reason = reason;
        this.namespace = namespace;
    }
}
