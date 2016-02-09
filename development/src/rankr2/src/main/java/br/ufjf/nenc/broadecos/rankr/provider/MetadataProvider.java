package br.ufjf.nenc.broadecos.rankr.provider;

import br.ufjf.nenc.broadecos.api.model.Metadata;
import br.ufjf.nenc.broadecos.api.model.Scope;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MetadataProvider {

    @Bean
    public Metadata metadata() {
         return Metadata.builder()
                 .id("rankr")
                 .name("Rankr")
                 .description("Shows conquest informations.")
                 .entryPoint("http://dev.broadecos:8090/#/index")
                 .redirectURI("http://dev.broadecos:8090/authorization/code")
                 .scope(Scope.PARTICIPANT_PROFILE)
                 .scope(Scope.COURSES_CURRENT)
                 .scope(Scope.EXPERIENCES_READ)
                 .scope(Scope.OFFLINE_ACCESS)
                 .build();
    }

}
