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

    private String version;

    private String entryPoint;

    private String authURI;

    private String redirectURI;

    private Icon icon;

    private Maintainer maintainer;

    private String moreInfo;

    @Singular
    private Set<RequestedScope> scopes;

    @Singular
    private Set<RequestedExtensions> extensions;

    private Object lom;
}
