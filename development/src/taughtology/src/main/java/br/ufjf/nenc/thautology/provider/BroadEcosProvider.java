package br.ufjf.nenc.thautology.provider;

import br.ufjf.nenc.broadecos.api.BroadEcosApi;
import br.ufjf.nenc.broadecos.api.BroadEcosApiProvider;
import br.ufjf.nenc.broadecos.api.model.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BroadEcosProvider {

    private final Metadata metadata;

    @Autowired
    public BroadEcosProvider(Metadata metadata) {
        this.metadata = metadata;
    }

    @Bean
    public BroadEcosApiProvider getBroadEcosApi(){
        return BroadEcosApiProvider.builder()
                .metadata(metadata)
                .build();
    }

}
