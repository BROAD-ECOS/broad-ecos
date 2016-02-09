package br.ufjf.nenc.thautology.provider;

import br.ufjf.nenc.broadecos.api.model.Metadata;
import br.ufjf.nenc.broadecos.api.model.Scope;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MetadataProvider {

    @Bean
    public Metadata metadata() {
        return Metadata.builder()
                .id("thautology")
                .name("Thautology")
                .description("A collaborative logic game.")
                .entryPoint("http://dev.broadecos:8080/#/index")
                .scope(Scope.PARTICIPANT_PROFILE)
                .scope(Scope.PARTICIPANT_EMAIL)
                .scope(Scope.COURSES_CURRENT)
                .scope(Scope.COURSES_CURRENT_PARTICIPANTES)
                .scope(Scope.EXPERIENCE_WRITE)
                .build();
    }


}
