package br.ufjf.nenc.lp.featuremodel.lo;

import br.ufjf.nenc.lp.core.pl.Feature;
import br.ufjf.nenc.lp.core.pl.annotation.FeatureValueType;
import br.ufjf.nenc.lp.core.pl.annotation.ParamValue;
import br.ufjf.nenc.lp.featuremodel.lo.tutorial.StepFeature;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class ConquestFeature implements Feature{

    @ParamValue(value = "repository", mandatory = true)
    private String repository;

    @FeatureValueType(ConquestType.class)
    private ConquestType type;

    public ConquestType getType() {
        return type;
    }

    public void setType(ConquestType type) {
        this.type = type;
    }

    public String getTypeName() {
        return type.getConquestTypeName();
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ConquestFeature{");
        sb.append("repository='").append(repository).append('\'');
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }
}
