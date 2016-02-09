package br.ufjf.nenc.broadecos.api.model;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonRootName;

import static com.google.common.base.Preconditions.checkArgument;

@Data
@JsonRootName("scope")
public class RequestedScope {

    private final String id;
    private final Boolean required;
    private final String reason;

    public RequestedScope(String id, Boolean required, String reason) {
        checkArgument(id!=null);
        checkArgument(required!=null);
        checkArgument(reason!=null);
        this.id = id;
        this.required = required;
        this.reason = reason;
    }

    public RequestedScope(Scope id, Boolean required, String reason) {
        this(id.scope(), required, reason);
    }
}
