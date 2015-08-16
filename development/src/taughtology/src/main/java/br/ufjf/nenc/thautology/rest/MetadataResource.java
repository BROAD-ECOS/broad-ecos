package br.ufjf.nenc.thautology.rest;

import br.ufjf.nenc.broadecos.service.Metadata;
import br.ufjf.nenc.broadecos.service.Scope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(maxAge = 3600)
@RestController
public class MetadataResource {

    @RequestMapping(value = "/metadata")
    public Metadata getMetadata(){
        return Metadata.builder()
                .name("Thautology")
                .description("A collaborative logic game.")
                .scope(Scope.PARTICIPANT_PROFILE)
                .scope(Scope.PARTICIPANT_EMAIL)
                .build();
    }


}
