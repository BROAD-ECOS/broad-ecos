package br.ufjf.nenc.lp.core.scorm;

import java.util.List;

public class TutorialData {

    private String title;

    private String description;

    private String repository;

    private String loId;

    private List<TutorialStep> steps;

    public TutorialData(String loId) {
        this.loId = loId;
    }

    public List<TutorialStep> getSteps() {
        return steps;
    }

    public void setSteps(List<TutorialStep> steps) {
        this.steps = steps;
    }

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

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getLoId() {
        return loId;
    }

    public void setLoId(String loId) {
        this.loId = loId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TutorialData{");
        sb.append("title='").append(title).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", repository='").append(repository).append('\'');
        sb.append(", loId='").append(loId).append('\'');
        sb.append(", steps=").append(steps);
        sb.append('}');
        return sb.toString();
    }
}
