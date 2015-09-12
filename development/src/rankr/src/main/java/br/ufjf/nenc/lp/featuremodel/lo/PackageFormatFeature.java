package br.ufjf.nenc.lp.featuremodel.lo;

import br.ufjf.nenc.lp.core.pl.Feature;
import br.ufjf.nenc.lp.core.pl.annotation.FeatureValueType;

public class PackageFormatFeature implements Feature {

    @FeatureValueType(PackageType.class)
    private PackageType format;

    public PackageType getFormat() {
        return format;
    }

    public void setFormat(PackageType format) {
        this.format = format;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PackageFormatFeature{");
        sb.append("format=").append(format);
        sb.append('}');
        return sb.toString();
    }
}
