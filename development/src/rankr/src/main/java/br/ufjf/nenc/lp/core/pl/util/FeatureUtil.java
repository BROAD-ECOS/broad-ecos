package br.ufjf.nenc.lp.core.pl.util;

import br.ufjf.nenc.lp.core.pl.Feature;
import br.ufjf.nenc.lp.core.pl.InvalidFeatureName;

import static com.google.common.base.Preconditions.checkNotNull;

public final class FeatureUtil {

    private static final String FEATURE_SUFFIX = "Feature";

    private FeatureUtil(){};

    public static String getName(Class<? extends Feature> featureClass) throws InvalidFeatureName {
        checkNotNull(featureClass);

        String className = featureClass.getName();
        String name = className.replaceAll(FEATURE_SUFFIX, "").toLowerCase();

        if(className.length() != name.length() + FEATURE_SUFFIX.length()) {
            throw new InvalidFeatureName(featureClass);
        }

        return name;
    }

}
