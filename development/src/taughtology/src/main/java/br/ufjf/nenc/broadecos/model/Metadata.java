package br.ufjf.nenc.broadecos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Metadata {

    private String name;

    private String description;

    @Singular
    @JsonIgnore
    private Set<Scope> scopes;

    @JsonProperty
    public Set<String> getScopes(){
        return scopes.stream().map(Scope::scope).collect(Collectors.toSet());
    }

}
