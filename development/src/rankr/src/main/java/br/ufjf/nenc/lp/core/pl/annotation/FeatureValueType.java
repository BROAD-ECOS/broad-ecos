package br.ufjf.nenc.lp.core.pl.annotation;

import br.ufjf.nenc.lp.core.pl.Feature;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FeatureValueType {
    Class<? extends Feature> value();

    boolean mandatory() default false;
}
