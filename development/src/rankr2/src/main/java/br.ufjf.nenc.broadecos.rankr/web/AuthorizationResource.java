package br.ufjf.nenc.broadecos.rankr.web;

import br.ufjf.nenc.broadecos.api.model.Metadata;
import br.ufjf.nenc.broadecos.api.model.Scope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(maxAge = 3600)
@RequestMapping(value = "/authorization")
@RestController
public class AuthorizationResource {
    @RequestMapping(value = "/code")
    public void getMetadata(@RequestParam("code") String code) {

    }
}
