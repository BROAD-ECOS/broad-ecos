package br.ufjf.nenc.lp.featuremodel.lo;


import br.ufjf.nenc.lp.featuremodel.lo.tutorial.StepFeature;
import com.google.common.collect.ImmutableMap;

import java.util.Collections;
import java.util.Map;

public class MedalsFeature extends ConquestType {

    public static final String MEDALS_TYPE_NAME = "medals";

    @Override
    public String getConquestTypeName() {
        return MEDALS_TYPE_NAME;
    }

    @Override
    public Map<String, String> getParams(StepFeature step) {
        return new ImmutableMap.Builder<String, String> ()
                .put("medal", step.getMedal()!=null?step.getMedal():"")
                .put("medalName", step.getMedal() != null ? step.getMedalName() : "")
                .put("value", step.getMedal()!=null?step.getValue():"").build();
    }
}
