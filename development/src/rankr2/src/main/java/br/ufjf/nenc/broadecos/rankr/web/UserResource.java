package br.ufjf.nenc.broadecos.rankr.web;

import br.ufjf.nenc.broadecos.rankr.exception.NotFoundException;
import br.ufjf.nenc.broadecos.rankr.model.CurrentUser;
import br.ufjf.nenc.broadecos.rankr.model.User;
import br.ufjf.nenc.broadecos.rankr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/me", method = GET)
    public User getMe(CurrentUser currentUser){
        return currentUser.getUser();
    }

    @RequestMapping(value = "/{id}", method = GET)
    public User getMe(@PathVariable("id") String userId){
        Optional<User> user = userService.getUser(userId);
        if (!user.isPresent()) {
            throw new NotFoundException();
        }
        return user.get();
    }
}
