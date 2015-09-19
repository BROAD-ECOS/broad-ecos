package br.ufjf.nenc.thautology.experience;

import br.ufjf.nenc.broadecos.experience.Account;
import br.ufjf.nenc.broadecos.experience.Actor;
import br.ufjf.nenc.broadecos.experience.Agent;

import java.net.URI;

public class TaughtologyAuthority {

    public Actor getAuthoriry() {
        Agent agent = new Agent();
        agent.setName("Taughtology");
        agent.setAccount(new Account(URI.create("http://dev.broadecos:8080/"),"Taughtology"));
        return agent;
    }

}
