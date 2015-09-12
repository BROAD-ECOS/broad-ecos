package br.ufjf.nenc.lp.core.entity;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

public class Conquest implements Serializable {

    @Id
    private String id;

    private String learningObjectId;

    private String profileId;

    private ConquestType type;

    private String value;

    private Date createdAt;

    private Date updatedAt;
    private String interaction;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLearningObjectId() {
        return learningObjectId;
    }

    public void setLearningObjectId(String learningObjectId) {
        this.learningObjectId = learningObjectId;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public ConquestType getType() {
        return type;
    }

    public void setType(ConquestType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public void setInteraction(String interaction) {
        this.interaction = interaction;
    }

    public String getInteraction() {
        return interaction;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Conquest{");
        sb.append("id='").append(id).append('\'');
        sb.append(", learningObjectId='").append(learningObjectId).append('\'');
        sb.append(", profileId='").append(profileId).append('\'');
        sb.append(", type=").append(type);
        sb.append(", value='").append(value).append('\'');
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", interaction='").append(interaction).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
