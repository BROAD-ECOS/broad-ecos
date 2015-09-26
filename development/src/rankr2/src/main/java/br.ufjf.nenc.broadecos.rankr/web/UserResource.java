package br.ufjf.nenc.broadecos.rankr.web;

import br.ufjf.nenc.broadecos.rankr.model.CurrentUser;
import br.ufjf.nenc.broadecos.rankr.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    @RequestMapping(value = "/me", method = GET)
    public User getMe(CurrentUser currentUser){
        return currentUser.getUser();
    }
}
