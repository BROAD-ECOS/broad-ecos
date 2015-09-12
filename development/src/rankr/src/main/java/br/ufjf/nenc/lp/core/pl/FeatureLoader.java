package br.ufjf.nenc.lp.core.pl;

import br.ufjf.nenc.lp.core.pl.util.FeatureUtil;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class FeatureLoader {

    private final String FEATURE_TYPE_NOT_FOUND_MSG = "Feature class not found for name '%s'";

    private Map<String, Class<? extends Feature>> featureMap;

    @PostConstruct
    public void init() {
        Reflections reflections = new Reflections(ClasspathHelper.forJavaClassPath());

        Set<Class<? extends Feature>> featureClassSet = reflections.getSubTypesOf(Feature.class);

        ImmutableMap.Builder<String, Class<? extends Feature>> builder = ImmutableMap.builder();
        for (Class<? extends Feature> featureClazz : featureClassSet) {
            if (!Modifier.isAbstract(featureClazz.getModifiers())) {
                builder.put(FeatureUtil.getName(featureClazz), featureClazz);
            }
        }

        featureMap = builder.build();
    }


    public Class<? extends Feature> getFeatureByType(String type) throws FeatureTypeNotFoundException {

        Class<? extends Feature> typeClazz = featureMap.get(type);

        if (typeClazz == null) {
            throw new FeatureTypeNotFoundException(String.format(FEATURE_TYPE_NOT_FOUND_MSG, type));
        }

        return typeClazz;
    }


    public Set<Class<? extends Feature>> getFeaturesWithType(Class<? extends Feature> filterType) throws FeatureTypeNotFoundException {

        Set<Class<? extends Feature>> featuresWithClass = new HashSet<>();

        for(Class<? extends Feature> type : featureMap.values()) {
            if (filterType.isAssignableFrom(type)) {
                featuresWithClass.add(type);
            }
        }

        return ImmutableSet.copyOf(featuresWithClass);
    }
}
