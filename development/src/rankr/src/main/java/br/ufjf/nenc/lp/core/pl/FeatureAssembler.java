package br.ufjf.nenc.lp.core.pl;

import br.ufjf.nenc.lp.core.pl.annotation.FeatureValue;
import br.ufjf.nenc.lp.core.pl.annotation.FeatureValueType;
import br.ufjf.nenc.lp.core.pl.annotation.FeatureValues;
import br.ufjf.nenc.lp.core.pl.annotation.ParamValue;
import br.ufjf.nenc.lp.core.pl.ir.FeatureDescriptor;
import br.ufjf.nenc.lp.core.pl.util.FeatureUtil;
import com.google.common.base.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;


@Component
public class FeatureAssembler {

    public static final String FAILURE_ASSEMBLING_MSG = "Failure assembling %s";


    public static final String MANDATORY_PARAM_MSG = "Param %s is mandatory for '%s' and is absent.";

    public static final String MISSED_PARAMS_MSG = "Unsupported params for feature '%s', received: '%s'";

    public static final String INVALID_PARAM_VALUE_PROPERTY_MSG = "Invalid property %s: @ParamValue property type should be String.";


    public static final String MANDATORY_FEATURE_MSG = "Feature '%s' is mandatory for '%s' and is absent.";

    public static final String MISSED_FEATURE_MSG = "Unsupported feature for '%s', received: '%s'";

    public static final String INVALID_FEATURE_VALUE_PROPERTY_MSG = "Invalid property %s: @FeatureValue property type should be a Feature subtype.";

    public static final String MULTIPLE_FEATURES_FOR_TYPE_MSG = "Multiple features ('%s') for type '%s' in '%s'";

    public static final String  MANDATORY_FEATURE_TYPE_MSG = "Feature for type '%s' is mandatory for '%s' but was not found.";


    @Autowired
    private FeatureLoader loader;


    public Feature assemble(FeatureDescriptor descriptor) throws FeatureAssembleException {
        Feature feature;

        try {
            Class<? extends Feature> featureClass = loader.getFeatureByType(descriptor.getName());
            feature = doAssemble(descriptor, featureClass);

        } catch (FeatureAssembleException e) {
            throw e;
        } catch (Exception e) {
            throw new FeatureAssembleException(String.format(FAILURE_ASSEMBLING_MSG, descriptor.getName()), e);
        }

        return feature;
    }

    private <T extends Feature> T doAssemble(FeatureDescriptor descriptor, Class<T> featureClass) throws Exception {

        T feature = featureClass.newInstance();

        setParams(descriptor, feature);

        setFeatures(descriptor, feature);

        return feature;
    }


    private <T extends Feature> void setParams(FeatureDescriptor descriptor, T feature) throws IllegalAccessException {

        List<String> settedProperties = new ArrayList<>();

        Field[] properties = feature.getClass().getDeclaredFields();

        for (Field prop : properties) {
            ParamValue paramValue = prop.getAnnotation(ParamValue.class);
            if (paramValue != null) {
                if (setParamValue(descriptor, feature, prop, paramValue)) {
                    settedProperties.add(paramValue.value());
                }
            }
        }

        validateDescriptorParams(descriptor.getParamNames(), settedProperties, feature);
    }

    private <T extends Feature> boolean setParamValue(FeatureDescriptor descriptor, T feature, Field prop, ParamValue paramValue) throws IllegalAccessException {

        boolean wasSet = false;

        if (prop.getType() == String.class) {
            Optional<String> value = descriptor.getParamValue(paramValue.value());
            if (value.isPresent() || !paramValue.mandatory()) {
                set(feature, prop, value.orNull());
                wasSet = true;
            } else {
                throw new FeatureAssembleException(String.format(MANDATORY_PARAM_MSG, feature.getClass().getName(), paramValue.value()));
            }
        } else {
            throw new FeatureAssembleException(String.format(INVALID_PARAM_VALUE_PROPERTY_MSG, prop));
        }

        return wasSet;
    }


    private <T extends Feature> void setFeatures(FeatureDescriptor descriptor, T feature) throws Exception {

        Set<String> settedProperties = new HashSet<>();
        Field[] properties = feature.getClass().getDeclaredFields();

        for (Field prop : properties) {

            String value = setFeatureValue(descriptor, feature, prop);
            if (value != null) {
                settedProperties.add(value);
            } else {
                value = setFeatureValues(descriptor, feature, prop);
                if (value != null) {
                    settedProperties.add(value);
                } else {
                    value = setFeatureValueType(descriptor, feature, prop);
                    if (value != null) {
                        settedProperties.add(value);
                    }
                }

            }
        }

        validateDescriptorFeatures(descriptor.getFeatureNames(), settedProperties, feature);
    }


    private <T extends Feature> String setFeatureValue(FeatureDescriptor descriptor, T feature, Field prop) throws Exception {
        String value = null;

        FeatureValue featureValue = prop.getAnnotation(FeatureValue.class);
        if (featureValue != null) {
            if (doSetFeatureValue(descriptor, feature, prop, featureValue)) {
                value = featureValue.value();
            }
        }

        return value;
    }


    private <T extends Feature> boolean doSetFeatureValue(FeatureDescriptor descriptor, T feature, Field prop, FeatureValue featureValue) throws Exception {

        boolean wasSet = false;

        Optional<FeatureDescriptor> featureDescriptor = descriptor.getFeature(featureValue.value());

        if (featureDescriptor.isPresent()) {
            if (Feature.class.isAssignableFrom(prop.getType())) {
                Feature childFeature = assemble(featureDescriptor.get());
                set(feature, prop, childFeature);
                wasSet = true;
            } else {
                throw new FeatureAssembleException(String.format(INVALID_FEATURE_VALUE_PROPERTY_MSG, prop));
            }
        } else {
            if (featureValue.mandatory()) {
                throw new FeatureAssembleException(String.format(MANDATORY_FEATURE_MSG, featureValue.value(), feature.getClass().getName()));
            }
        }

        return wasSet;
    }

    private <T extends Feature> String setFeatureValues(FeatureDescriptor descriptor, T feature, Field prop) throws Exception {
        String value = null;

        FeatureValues featureValues = prop.getAnnotation(FeatureValues.class);
        if (featureValues != null) {
            if (doSetFeatureValues(descriptor, feature, prop, featureValues)) {
                value = featureValues.value();
            }
        }

        return value;
    }

    private <T extends Feature> boolean doSetFeatureValues(FeatureDescriptor descriptor, T feature, Field prop, FeatureValues featureValues) throws Exception {

        boolean wasSet = false;

        List<FeatureDescriptor> descriptors = descriptor.getFeatures(featureValues.value());

        if (!descriptors.isEmpty() || !featureValues.mandatory()) {
            if (Collection.class.isAssignableFrom(prop.getType())) {

                Collection<Feature> features = new ArrayList<>();

                for (FeatureDescriptor childDescriptor : descriptors) {
                    features.add(assemble(childDescriptor));
                }

                set(feature, prop, features);
                wasSet = true;
            } else {
                throw new FeatureAssembleException(String.format(INVALID_FEATURE_VALUE_PROPERTY_MSG, prop));
            }
        } else {
            throw new FeatureAssembleException(String.format(MANDATORY_FEATURE_MSG, featureValues.value(), feature.getClass().getName()));
        }

        return wasSet;
    }

    private <T extends Feature> String setFeatureValueType(FeatureDescriptor descriptor, T feature, Field prop) throws Exception{
        String value = null;

        FeatureValueType featureValueType = prop.getAnnotation(FeatureValueType.class);
        if (featureValueType != null) {
            value = doSetFeatureValueType(descriptor, feature, prop, featureValueType);
        }

        return value;
    }

    private <T extends Feature> String doSetFeatureValueType(FeatureDescriptor descriptor, T feature, Field prop, FeatureValueType featureValueType) throws Exception {
        String propSet = null;

        Set<String> supportedNames = new HashSet<>();
        for(Class<? extends Feature>  type : loader.getFeaturesWithType(featureValueType.value())) {
            supportedNames.add(FeatureUtil.getName(type));
        }

        Set<String> featureNames = new HashSet<>(descriptor.getFeatureNames());
        featureNames.retainAll(supportedNames);


        if (!featureNames.isEmpty() || !featureValueType.mandatory()) {
            if (featureNames.size() == 1) {
                propSet = featureNames.iterator().next();
                FeatureDescriptor childFeatureDescription = descriptor.getFeature(propSet).get();
                Feature childFeature = assemble(childFeatureDescription);
                set(feature, prop, childFeature);
            } else if (featureValueType.mandatory()){
                String msg = String.format(MULTIPLE_FEATURES_FOR_TYPE_MSG, featureNames, featureValueType.value(), feature.getClass().getName());
                throw new FeatureAssembleException(msg);
            }
        } else {
            throw new FeatureAssembleException(String.format(MANDATORY_FEATURE_TYPE_MSG, featureValueType.value(), feature.getClass().getName()));
        }

        return propSet;
    }

    private <T extends Feature> void validateDescriptorParams(List<String> descriptorParams, List<String> settedProperties, T feature) {
        if (!settedProperties.containsAll(descriptorParams)) {
            List<String> missedParams = new ArrayList<>(descriptorParams);
            missedParams.removeAll(settedProperties);
            throw new FeatureAssembleException(String.format(MISSED_PARAMS_MSG, feature.getClass().getName(), missedParams));
        }
    }

    private <T extends Feature> void validateDescriptorFeatures(Set<String> featureNames, Set<String> settedProperties, T feature) {
        if (!settedProperties.containsAll(featureNames)) {
            List<String> missedParams = new ArrayList<>(featureNames);
            missedParams.removeAll(settedProperties);
            throw new FeatureAssembleException(String.format(MISSED_FEATURE_MSG, feature.getClass().getName(), missedParams));
        }
    }

    private <T extends Feature> void set(T feature, Field prop, Object childFeature) throws IllegalAccessException {
        prop.setAccessible(true);
        prop.set(feature, childFeature);
        prop.setAccessible(false);
    }

}
