package br.ufjf.nenc.thautology.provider;

import br.ufjf.nenc.broadecos.BroadEcosApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class LocaleProvider {

    @Value("locale.lang")
    private String country;

    @Value("locale.country")
    private String lang;

    @Bean
    public Locale currentLocale(){
        return new Locale(lang, country);
    }

}
