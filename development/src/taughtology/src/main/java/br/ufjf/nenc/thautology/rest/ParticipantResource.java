package br.ufjf.nenc.thautology.rest;

import br.ufjf.nenc.broadecos.BroadEcosApi;
import br.ufjf.nenc.broadecos.Context;
import br.ufjf.nenc.broadecos.model.ParticipantProfile;
import br.ufjf.nenc.thautology.model.CurrentUser;
import br.ufjf.nenc.thautology.model.User;
import br.ufjf.nenc.thautology.service.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

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
