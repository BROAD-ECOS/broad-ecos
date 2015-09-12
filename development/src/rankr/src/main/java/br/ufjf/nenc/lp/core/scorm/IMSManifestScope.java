package br.ufjf.nenc.lp.core.scorm;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class IMSManifestScope {

    private String title;

    private String description;

    private Organization organization;

    private Set<Resource> resources = new HashSet<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Set<Resource> getResources() {
        return Collections.unmodifiableSet(resources);
    }

    public void addResource(Resource resource) {
        resources.add(resource);
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IMSManifestScope{");
        sb.append("title='").append(title).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", organization=").append(organization);
        sb.append(", resources=").append(resources);
        sb.append('}');
        return sb.toString();
    }

}
