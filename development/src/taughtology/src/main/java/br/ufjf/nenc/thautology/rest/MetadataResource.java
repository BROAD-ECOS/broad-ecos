package br.ufjf.nenc.thautology.rest;

import br.ufjf.nenc.broadecos.api.model.Metadata;
import br.ufjf.nenc.broadecos.api.model.Scope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(maxAge = 3600)
@RestController
public class MetadataResource {

    @RequestMapping(value = "/metadata")
    public Metadata getMetadata(){
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
