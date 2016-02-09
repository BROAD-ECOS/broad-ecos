package br.ufjf.nenc.broadecos.rankr.web;

import br.ufjf.nenc.broadecos.rankr.model.Conquest;
import br.ufjf.nenc.broadecos.rankr.service.ConquestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/conquests")
public class ConquestResource {

    private final ConquestService conquestService;

    @Autowired
    public ConquestResource(ConquestService conquestService) {
        this.conquestService = conquestService;
    }


    @RequestMapping(method = GET)
    public Page<Conquest> get(@RequestParam("userId") Optional<String> userId, @RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize){
        final PageRequest pageRequest = new PageRequest(page, pageSize);
        return conquestService.all(userId, pageRequest);
    }

}
