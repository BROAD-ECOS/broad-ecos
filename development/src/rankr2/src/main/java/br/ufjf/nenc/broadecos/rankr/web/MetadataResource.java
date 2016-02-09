package br.ufjf.nenc.broadecos.rankr.web;

import br.ufjf.nenc.broadecos.api.model.Metadata;
import br.ufjf.nenc.broadecos.api.model.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(maxAge = 3600)
@RestController
public class MetadataResource {

    private final Metadata metadata;

    @Autowired
    public MetadataResource(Metadata metadata) {
        this.metadata = metadata;
    }

    @RequestMapping(value = "/metadata")
    public Metadata getMetadata(){
        return metadata;
    }
}
