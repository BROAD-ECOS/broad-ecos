package br.ufjf.nenc.lp.web.rest;

import br.ufjf.nenc.broadecos.api.model.Metadata;
import br.ufjf.nenc.broadecos.api.model.Scope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(maxAge = 3600)
@RestController
public class MetadataRest {

    @RequestMapping(value = "/metadata")
    public Metadata getMetadata(){
        return Metadata.builder()
                .id("Rankr")
                .name("Rankr")
                .description("Shows conquest informations.")
                .entryPoint("http://dev.broadecos:8090/#/index")
                .scope(Scope.PARTICIPANT_PROFILE)
                .scope(Scope.COURSES_CURRENT)
                .scope(Scope.EXPERIENCES_READ)
                .scope(Scope.OFFLINE_ACCESS)
                .build();
    }


}
