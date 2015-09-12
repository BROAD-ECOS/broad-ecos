package br.ufjf.nenc.lp.featuremodel.lo.tutorial;

import br.ufjf.nenc.lp.core.pl.annotation.FeatureValue;
import br.ufjf.nenc.lp.core.pl.annotation.FeatureValues;
import br.ufjf.nenc.lp.featuremodel.lo.LearningObjectType;

import java.util.Collection;

public class TutorialFeature extends LearningObjectType {

    @FeatureValues("br.ufjf.nenc.lp.featuremodel.lo.tutorial.step")
    Collection<StepFeature> steps;

    @FeatureValue("br.ufjf.nenc.lp.featuremodel.lo.tutorial.index")
    IndexFeature index;

    @FeatureValue("br.ufjf.nenc.lp.featuremodel.lo.tutorial.sequential")
    SequentialFeature sequential;

    public Collection<StepFeature> getSteps() {
        return steps;
    }

    public void setSteps(Collection<StepFeature> steps) {
        this.steps = steps;
    }

    public IndexFeature getIndex() {
        return index;
    }

    public void setIndex(IndexFeature index) {
        this.index = index;
    }

    public SequentialFeature getSequential() {
        return sequential;
    }

    public void setSequential(SequentialFeature sequential) {
        this.sequential = sequential;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TutorialFeature{");
        sb.append("steps=").append(steps);
        sb.append(", index=").append(index);
        sb.append(", sequential=").append(sequential);
        sb.append('}');
        return sb.toString();
    }
}
