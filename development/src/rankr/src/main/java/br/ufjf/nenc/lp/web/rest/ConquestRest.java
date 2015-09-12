package br.ufjf.nenc.lp.web.rest;

import br.ufjf.nenc.lp.core.entity.Conquest;
import br.ufjf.nenc.lp.core.service.ConquestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conquests")
public class ConquestRest {

    public final ConquestService service;

    @Autowired
    public ConquestRest(ConquestService service) {
        this.service = service;
    }

    @RequestMapping( method = RequestMethod.GET)
    public @ResponseBody List<Conquest> query(@RequestParam(value="profileId", required = true) String profileId) {
        return service.findAllByProfileId(profileId);
    }



}
