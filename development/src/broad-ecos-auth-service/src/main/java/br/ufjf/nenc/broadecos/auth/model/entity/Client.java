package br.ufjf.nenc.broadecos.auth.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Entity
public class Client  implements Serializable{

    @Id
    @GeneratedValue
    private String id;

    private String clientId;

    @ElementCollection
    @CollectionTable(
            name="client_resource",
            joinColumns=@JoinColumn(name="client_id")
    )
    @Column(name="resource_name")
    private Set<String> resourceIds;

    private boolean secretRequired;

    private String clientSecret;

    private boolean scoped;

    private String scope;

    @ElementCollection
    @CollectionTable(
            name="client_authorized_grant_type",
            joinColumns=@JoinColumn(name="client_id")
    )
    @Column(name="authorized_grant_type_name")
    private Set<String> authorizedGrantTypes;


    @ElementCollection
    @CollectionTable(
            name="client_registered_redirect_uri",
            joinColumns=@JoinColumn(name="client_id")
    )
    @Column(name="registered_redirect_uri")
    private Set<String> registeredRedirectUris;

    @ElementCollection
    @CollectionTable(
            name="client_authorities",
            joinColumns=@JoinColumn(name="client_id")
    )
    @Column(name="authority")
    private Set<String> authorities;

    private Integer accessTokenValiditySeconds;

    private Integer refreshTokenValiditySeconds;


    @CollectionTable(name = "client_additional_information", joinColumns = @JoinColumn(name =  "client_id"))
    @MapKeyColumn(name = "info_name", length = 50)
    @Column(name = "info_value", length = 100)
    private Map<String, String> additionalInformation;

    public Client() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Set<String> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(Set<String> resourceIds) {
        this.resourceIds = resourceIds;
    }

    public boolean isSecretRequired() {
        return secretRequired;
    }

    public void setSecretRequired(boolean secretRequired) {
        this.secretRequired = secretRequired;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public boolean isScoped() {
        return scoped;
    }

    public void setScoped(boolean scoped) {
        this.scoped = scoped;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Set<String> getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public Set<String> getRegisteredRedirectUris() {
        return registeredRedirectUris;
    }

    public void setRegisteredRedirectUris(Set<String> registeredRedirectUris) {
        this.registeredRedirectUris = registeredRedirectUris;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }

    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
    }

    public Map<String, String> getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(Map<String, String> additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(secretRequired, client.secretRequired) &&
                Objects.equals(scoped, client.scoped) &&
                Objects.equals(id, client.id) &&
                Objects.equals(clientId, client.clientId) &&
                Objects.equals(resourceIds, client.resourceIds) &&
                Objects.equals(clientSecret, client.clientSecret) &&
                Objects.equals(scope, client.scope) &&
                Objects.equals(authorizedGrantTypes, client.authorizedGrantTypes) &&
                Objects.equals(registeredRedirectUris, client.registeredRedirectUris) &&
                Objects.equals(authorities, client.authorities) &&
                Objects.equals(accessTokenValiditySeconds, client.accessTokenValiditySeconds) &&
                Objects.equals(refreshTokenValiditySeconds, client.refreshTokenValiditySeconds) &&
                Objects.equals(additionalInformation, client.additionalInformation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientId, resourceIds, secretRequired, clientSecret, scoped, scope, authorizedGrantTypes, registeredRedirectUris, authorities, accessTokenValiditySeconds, refreshTokenValiditySeconds, additionalInformation);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id='" + id + '\'' +
                ", clientId='" + clientId + '\'' +
                ", resourceIds=" + resourceIds +
                ", secretRequired=" + secretRequired +
                ", clientSecret='" + clientSecret + '\'' +
                ", scoped=" + scoped +
                ", scope='" + scope + '\'' +
                ", authorizedGrantTypes=" + authorizedGrantTypes +
                ", registeredRedirectUris=" + registeredRedirectUris +
                ", authorities=" + authorities +
                ", accessTokenValiditySeconds=" + accessTokenValiditySeconds +
                ", refreshTokenValiditySeconds=" + refreshTokenValiditySeconds +
                ", additionalInformation=" + additionalInformation +
                '}';
    }
}