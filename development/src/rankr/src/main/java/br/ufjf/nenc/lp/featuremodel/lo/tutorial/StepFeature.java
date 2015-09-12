package br.ufjf.nenc.lp.featuremodel.lo.tutorial;


import br.ufjf.nenc.lp.core.pl.Feature;
import br.ufjf.nenc.lp.core.pl.annotation.ParamValue;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class StepFeature implements Feature {

    @ParamValue("contentType")
    private String contentType;

    @ParamValue("name")
    private String name;

    @ParamValue("content")
    private String content;

    @ParamValue(value="points", mandatory = false)
    private String points;

    @ParamValue(value="medal", mandatory = false)
    private String medal;

    @ParamValue(value="medalName", mandatory = false)
    private String medalName;

    @ParamValue(value="value", mandatory = false)
    private String value;

    @ParamValue("show")
    private String show;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getMedal() {
        return medal;
    }

    public void setMedal(String medal) {
        this.medal = medal;
    }

    public String getMedalName() {
        return medalName;
    }

    public void setMedalName(String medalName) {
        this.medalName = medalName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StepFeature{");
        sb.append("contentType='").append(contentType).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", points='").append(points).append('\'');
        sb.append(", medal='").append(medal).append('\'');
        sb.append(", medalName='").append(medalName).append('\'');
        sb.append(", value='").append(value).append('\'');
        sb.append(", show='").append(show).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public Map<String, String> getConquestParams() {
        return new ImmutableMap.Builder<String, String> ()
                .put("points", getPoints() != null ? getPoints() : "")
                .put("medal", getMedal()!=null? getMedal():"")
                .put("medalName", getMedalName()!=null?getMedalName():"")
                .put("value", getValue()!=null?getValue():"").build();
    }
}
