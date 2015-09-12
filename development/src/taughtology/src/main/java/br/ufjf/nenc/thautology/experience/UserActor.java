package br.ufjf.nenc.thautology.experience;

import br.ufjf.nenc.broadecos.experience.Account;
import br.ufjf.nenc.broadecos.experience.Actor;
import br.ufjf.nenc.broadecos.experience.Agent;
import br.ufjf.nenc.thautology.model.User;

import java.net.URI;

public class UserActor {

    private final User user;


    public UserActor(User user) {
        this.user = user;
    }

    public Actor toActor(){
        Agent agent = new Agent();
        agent.setName(user.getFullName());
        agent.setAccount(Account.builder()
                .homePage(URI.create(user.getPlatform()))
                .name(user.getParticipantId())
                .build()
        );
        return agent;
    }
}
