package br.ufjf.nenc.lp.featuremodel.lo;

import br.ufjf.nenc.lp.core.pl.Feature;
import br.ufjf.nenc.lp.core.pl.PackageFormat;
import br.ufjf.nenc.lp.core.pl.Packageable;
import br.ufjf.nenc.lp.core.pl.annotation.FeatureValue;
import br.ufjf.nenc.lp.core.pl.annotation.ParamValue;

public class LearningObjectFeature implements Feature, Packageable {

    @ParamValue(value = "loId", mandatory = true)
    private String id;

    @FeatureValue(value="br.ufjf.nenc.lp.featuremodel.lo.metadata", mandatory = true)
    public MetadataFeature metadata;

    @FeatureValue(value="br.ufjf.nenc.lp.featuremodel.lo.type", mandatory = true)
    public TypeFeature type;

    @FeatureValue(value="br.ufjf.nenc.lp.featuremodel.lo.conquest", mandatory = false)
    public ConquestFeature conquest;

    @FeatureValue(value="br.ufjf.nenc.lp.featuremodel.lo.packageformat", mandatory = true)
    public PackageFormatFeature packageformat;

    @Override
    public PackageFormat getPackageFormat() {
        return getPackageformat().getFormat();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MetadataFeature getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataFeature metadata) {
        this.metadata = metadata;
    }

    public TypeFeature getType() {
        return type;
    }

    public void setType(TypeFeature type) {
        this.type = type;
    }

    public PackageFormatFeature getPackageformat() {
        return packageformat;
    }

    public void setPackageformat(PackageFormatFeature packageformat) {
        this.packageformat = packageformat;
    }

    public ConquestFeature getConquest() {
        return conquest;
    }

    public void setConquest(ConquestFeature conquest) {
        this.conquest = conquest;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LearningObjectFeature{");
        sb.append("id='").append(id).append('\'');
        sb.append(", metadata=").append(metadata);
        sb.append(", type=").append(type);
        sb.append(", conquest=").append(conquest);
        sb.append(", packageformat=").append(packageformat);
        sb.append('}');
        return sb.toString();
    }
}
