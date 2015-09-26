package br.ufjf.nenc.thautology.rest;

import br.ufjf.nenc.broadecos.api.model.ParticipantProfile;
import br.ufjf.nenc.thautology.model.CurrentUser;
import br.ufjf.nenc.thautology.model.User;
import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/users")
@Log4j
public class ParticipantResource {

    @RequestMapping(value = "/me", method = GET)
    public User getMe(CurrentUser currentUser){
        return currentUser.getUser();
    }

}
