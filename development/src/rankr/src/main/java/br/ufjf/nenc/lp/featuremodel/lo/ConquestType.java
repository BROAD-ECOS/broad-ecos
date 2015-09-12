package br.ufjf.nenc.lp.featuremodel.lo;

import br.ufjf.nenc.lp.core.pl.Feature;
import br.ufjf.nenc.lp.featuremodel.lo.tutorial.StepFeature;

import java.util.Map;


public abstract class ConquestType implements Feature {

    public abstract String getConquestTypeName();

    public abstract Map<String, String> getParams(StepFeature step);
}
