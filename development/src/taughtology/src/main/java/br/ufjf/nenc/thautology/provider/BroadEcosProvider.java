package br.ufjf.nenc.thautology.provider;

import br.ufjf.nenc.broadecos.api.BroadEcosApi;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BroadEcosProvider {

    @Bean
    public BroadEcosApi getBroadEcosApi(){
        return BroadEcosApi.builder().build();
    }

}
