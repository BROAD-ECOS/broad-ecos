package br.ufjf.nenc.lp.featuremodel.lo;

import br.ufjf.nenc.lp.core.pl.annotation.ParamValue;

public class ScormFeature extends PackageType {

    @ParamValue(value = "format", mandatory = true)
    private String format;

    @Override
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ScormFeature{");
        sb.append("format='").append(format).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
