package br.ufjf.nenc.lp.featuremodel.lo;


import br.ufjf.nenc.lp.core.pl.annotation.ParamValue;
import br.ufjf.nenc.lp.featuremodel.lo.tutorial.StepFeature;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class PointsFeature extends ConquestType {

    public static final String CONQUEST_TYPE_NAME = "points";

    @ParamValue(value = "name", mandatory = false)
    private String name = "Points";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getConquestTypeName() {
        return CONQUEST_TYPE_NAME;
    }

    @Override
    public Map<String, String> getParams(StepFeature step) {
        return ImmutableMap.of("name", getName());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PointsFeature{");
        sb.append("name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
