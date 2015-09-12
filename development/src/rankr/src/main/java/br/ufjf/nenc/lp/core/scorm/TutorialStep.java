package br.ufjf.nenc.lp.core.scorm;



import java.util.Map;

public class TutorialStep {

    private String contentType;

    private Map<String, String> contentParams;

    private String conquestType;

    private Map<String, String> conquestParams;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Map<String, String> getContentParams() {
        return contentParams;
    }

    public void setContentParams(Map<String, String> contentParams) {
        this.contentParams = contentParams;
    }

    public String getConquestType() {
        return conquestType;
    }

    public void setConquestType(String conquestType) {
        this.conquestType = conquestType;
    }

    public Map<String, String> getConquestParams() {
        return conquestParams;
    }

    public void setConquestParams(Map<String, String> conquestParams) {
        this.conquestParams = conquestParams;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TutorialStep{");
        sb.append("contentType='").append(contentType).append('\'');
        sb.append(", contentParams=").append(contentParams);
        sb.append(", conquestType='").append(conquestType).append('\'');
        sb.append(", conquestParams=").append(conquestParams);
        sb.append('}');
        return sb.toString();
    }
}
