package br.ufjf.nenc.thautology.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class LocaleProvider {

    @Value("${taughtology.locale.lang}")
    private String country;

    @Value("${taughtology.locale.country}")
    private String lang;

    @Bean
    public Locale currentLocale(){
        return new Locale(country, lang);
    }

}
