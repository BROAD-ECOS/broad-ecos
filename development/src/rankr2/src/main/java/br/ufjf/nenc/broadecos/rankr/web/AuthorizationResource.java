package br.ufjf.nenc.broadecos.rankr.web;

import br.ufjf.nenc.broadecos.api.model.Metadata;
import br.ufjf.nenc.broadecos.api.model.Scope;
import br.ufjf.nenc.broadecos.rankr.service.BroadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(maxAge = 3600)
@RequestMapping(value = "/authorization")
@RestController
public class AuthorizationResource {

    private final BroadService broadService;

    @Autowired
    public AuthorizationResource(BroadService broadService) {
        this.broadService = broadService;
    }

    @RequestMapping(value = "/code")
    public void getMetadata(@RequestParam("code") String code, @RequestParam("platform") String platform, @RequestParam("course_id") String course) {
        broadService.completeAuthRequest(platform, course, code);
    }
}
