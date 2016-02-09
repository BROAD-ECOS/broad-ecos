package br.ufjf.nenc.broadecos.rankr.web;

import br.ufjf.nenc.broadecos.rankr.model.Conquest;
import br.ufjf.nenc.broadecos.rankr.model.ConquestCount;
import br.ufjf.nenc.broadecos.rankr.service.ConquestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/conquests-count")
public class ConquestCountResource {

    private final ConquestService conquestService;

    @Autowired
    public ConquestCountResource(ConquestService conquestService) {
        this.conquestService = conquestService;
    }

    @RequestMapping(path = "/{userId}", method = GET)
    public ConquestCount get(@PathVariable("userId") String userId){
        return conquestService.count(userId);
    }

}
