package br.ufjf.nenc.lp.core.entity;

import br.ufjf.nenc.lp.core.pl.ir.FeatureDescriptor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class LearningObject implements Serializable {

    @Id
    private String id;

    private String project;

    private List<FeatureDescriptor> feature;

    private String author;

    private String authorId;

    private Date createdAt;

    private Date updatedAt;

    public String getId() {
        return id;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public List<FeatureDescriptor> getFeature() {
        return feature;
    }

    public void setFeature(List<FeatureDescriptor> feature) {
        this.feature = feature;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LearningObject{");
        sb.append("id='").append(id).append('\'');
        sb.append(", project='").append(project).append('\'');
        sb.append(", feature=").append(feature);
        sb.append(", author='").append(author).append('\'');
        sb.append(", authorId='").append(authorId).append('\'');
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append('}');
        return sb.toString();
    }
}
