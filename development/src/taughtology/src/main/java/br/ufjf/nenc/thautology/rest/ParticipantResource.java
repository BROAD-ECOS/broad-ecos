package br.ufjf.nenc.thautology.rest;

import br.ufjf.nenc.broadecos.model.Participant;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/users")
public class ParticipantResource {


    @RequestMapping(value = "/me")
    public Participant getUser(){
        return null;
    }


}
