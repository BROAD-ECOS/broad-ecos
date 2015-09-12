package br.ufjf.nenc.lp.featuremodel.lo;

import br.ufjf.nenc.lp.core.pl.Feature;
import br.ufjf.nenc.lp.core.pl.annotation.FeatureValue;

public class MetadataFeature implements Feature {

    @FeatureValue(value="br.ufjf.nenc.lp.featuremodel.lo.lom", mandatory = false)
    public LOMFeature lom;

    public LOMFeature getLom() {
        return lom;
    }

    public void setLom(LOMFeature lom) {
        this.lom = lom;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MetadataFeature{");
        sb.append("lom=").append(lom);
        sb.append('}');
        return sb.toString();
    }
}
