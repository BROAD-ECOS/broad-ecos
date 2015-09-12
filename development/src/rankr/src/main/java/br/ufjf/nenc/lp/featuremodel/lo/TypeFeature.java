package br.ufjf.nenc.lp.featuremodel.lo;

import br.ufjf.nenc.lp.core.pl.Feature;
import br.ufjf.nenc.lp.core.pl.annotation.FeatureValueType;

public class TypeFeature implements Feature {

    @FeatureValueType(LearningObjectType.class)
    private LearningObjectType type;

    public LearningObjectType getType() {
        return type;
    }

    public void setType(LearningObjectType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TypeFeature{");
        sb.append("type=").append(type);
        sb.append('}');
        return sb.toString();
    }
}
