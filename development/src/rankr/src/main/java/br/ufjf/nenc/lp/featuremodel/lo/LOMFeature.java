package br.ufjf.nenc.lp.featuremodel.lo;

import br.ufjf.nenc.lp.core.pl.Feature;
import br.ufjf.nenc.lp.core.pl.annotation.ParamValue;

public class LOMFeature implements Feature {

    @ParamValue(value = "title", mandatory = true)
    private String title;

    @ParamValue(value = "description", mandatory = false)
    private String description;

    @ParamValue(value = "organizationId", mandatory = false)
    private String organizationId;

    @ParamValue(value = "organizationName", mandatory = false)
    private String organizationName;

    @ParamValue(value = "language", mandatory = false)
    private String language;

    @ParamValue(value = "keywords", mandatory = false)
    private String keywords;

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

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LOMFeature{");
        sb.append("title='").append(title).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", organizationId='").append(organizationId).append('\'');
        sb.append(", organizationName='").append(organizationName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
