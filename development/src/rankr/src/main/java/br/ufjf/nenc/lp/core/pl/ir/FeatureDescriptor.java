package br.ufjf.nenc.lp.core.pl.ir;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

@XmlRootElement(name = "feature")
public class FeatureDescriptor {

    private String name;

    private List<FeatureDescriptor> feature;

    private Params params;

    private Boolean selected;

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FeatureDescriptor> getFeature() {
        return feature;
    }

    public void setFeature(List<FeatureDescriptor> feature) {
        this.feature = feature;
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Optional<FeatureDescriptor> getFeature(String name) {
        FeatureDescriptor f = null;
        for(FeatureDescriptor it : feature) {
            if (it.getSelected() && it.getName().equals(name)) {
                f = it;
                break;
            }
        }
        return Optional.fromNullable(f);
    }

    public List<FeatureDescriptor> getFeatures(String name) {
        List<FeatureDescriptor> features = new ArrayList<>();
        for(FeatureDescriptor it : feature) {
            if (it.getSelected() && it.getName().equals(name)) {
                features.add(it);
            }
        }
        return Collections.unmodifiableList(features);
    }

    public Optional<String> getParamValue(String paramName) {
        String value = null;
        for (Param param : params.getParam()) {
            if (param.getName().equals(paramName)) {
                value = param.getValue();
                break;
            }
        }
        return Optional.fromNullable(value);
    }

    @JsonIgnore
    public List<String> getParamNames() {
        List<String> names = Collections.emptyList();

        if (params != null) {
            names = new ArrayList<>(params.getParam().size());
            for (Param param : params.getParam()) {
                names.add(param.getName());
            }
        }

        return Collections.unmodifiableList(names);
    }

    @JsonIgnore
    public Set<String> getFeatureNames() {
        Set<String> names = Collections.emptySet();

        if(getFeature() != null) {
            names = new HashSet<>(getFeature().size());
            for (FeatureDescriptor descriptor : getFeature()) {
                if (descriptor.getSelected()) {
                    names.add(descriptor.getName());
                }
            }
        }

        return ImmutableSet.copyOf(names);
    }

    @Override
    public String toString() {
        return "FeatureDescriptor{" +
                "name='" + name + '\'' +
                ", feature=" + feature +
                ", params=" + params +
                '}';
    }
}
