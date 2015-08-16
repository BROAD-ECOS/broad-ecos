package br.ufjf.nenc.broadecos.auth.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

import static javax.persistence.FetchType.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client  implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String clientId;

    @ElementCollection(fetch = EAGER)
    @CollectionTable(
            name="client_resource",
            joinColumns=@JoinColumn(name="client_id")
    )
    @Column(name="resource_name")
    private Set<String> resourceIds = new HashSet<>();

    private boolean secretRequired;

    private String clientSecret;

    private boolean scoped;

    @ElementCollection(fetch = EAGER)
    @CollectionTable(
            name="client_scope",
            joinColumns=@JoinColumn(name="client_id")
    )
    @Column(name="scope_name")
    @Singular
    private Set<String> scopes = new HashSet<>();

    @ElementCollection(fetch = EAGER)
    @CollectionTable(
            name="client_authorized_grant_type",
            joinColumns=@JoinColumn(name="client_id")
    )
    @Column(name="authorized_grant_type_name")
    @Singular
    private Set<String> authorizedGrantTypes = new HashSet<>();


    @ElementCollection(fetch = EAGER)
    @CollectionTable(
            name="client_registered_redirect_uri",
            joinColumns=@JoinColumn(name="client_id")
    )
    @Column(name="registered_redirect_uri")
    private Set<String> registeredRedirectUris = new HashSet<>();

    @ElementCollection(fetch = EAGER)
    @CollectionTable(
            name="client_authorities",
            joinColumns=@JoinColumn(name="client_id")
    )
    @Column(name="authority")
    private Set<String> authorities = new HashSet<>();

    private Integer accessTokenValiditySeconds;

    private Integer refreshTokenValiditySeconds;


    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "client_additional_information", joinColumns = @JoinColumn(name =  "client_id"))
    @MapKeyColumn(name = "info_name", length = 50)
    @Column(name = "info_value", length = 100)
    private Map<String, String> additionalInformation = new HashMap<>();


    public boolean hasAccessAllowed() {
        return true;
    }
}