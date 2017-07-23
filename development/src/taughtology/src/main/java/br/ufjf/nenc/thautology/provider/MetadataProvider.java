package br.ufjf.nenc.thautology.provider;

import br.ufjf.nenc.broadecos.api.model.Metadata;
import br.ufjf.nenc.broadecos.api.model.RequestedScope;
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
                .entryPoint("http://localhost:8080/#/index")
                .scope(new RequestedScope(Scope.PARTICIPANT_PROFILE, true, ""))
                .scope(new RequestedScope(Scope.PARTICIPANT_EMAIL, true, ""))
                .scope(new RequestedScope(Scope.COURSES_CURRENT, true, ""))
                .scope(new RequestedScope(Scope.COURSES_CURRENT_PARTICIPANTES, true, ""))
                .scope(new RequestedScope(Scope.EXPERIENCE_WRITE, true, ""))
                .build();
    }
}
