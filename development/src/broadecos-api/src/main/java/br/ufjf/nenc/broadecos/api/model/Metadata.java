package br.ufjf.nenc.broadecos.api.model;

import lombok.*;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Metadata {

    private String id;

    private String name;

    private String description;

    private String entryPoint;

    private String redirectURI;

    @Singular
    @JsonIgnore
    private Set<Scope> scopes;

    @JsonProperty
    public Set<String> getScopes(){
        return scopes.stream().map(Scope::scope).collect(Collectors.toSet());
    }

}
