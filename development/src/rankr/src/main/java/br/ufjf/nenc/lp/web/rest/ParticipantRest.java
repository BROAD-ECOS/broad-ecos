package br.ufjf.nenc.lp.web.rest;

import br.ufjf.nenc.lp.core.entity.Participant;
import br.ufjf.nenc.lp.core.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participants")
public class ParticipantRest {

    @Autowired
    public ParticipantService service;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody Participant post(@RequestBody Participant descriptor) {
        return service.save(descriptor);
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Participant> query(
            @RequestParam(value="id", required = false) String id,
            @RequestParam(value="email", required = false) String email) {
        return service.findBy(id, email);
    }


}
