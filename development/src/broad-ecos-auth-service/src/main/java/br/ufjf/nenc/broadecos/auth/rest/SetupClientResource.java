package br.ufjf.nenc.broadecos.auth.rest;

import br.ufjf.nenc.broadecos.auth.model.entity.Client;
import br.ufjf.nenc.broadecos.auth.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@RestController
@EnableResourceServer
public class SetupClientResource {

    @Autowired
    private ClientRepository repository;

    @RequestMapping("/clientsetup")
    public List<Client> get() {

        repository.deleteAll();

        List<Client> clients = new ArrayList<>();

        clients.add(Client.builder()
                .clientId("broad-ecos")
                .clientSecret("s3cr3t")
                .authorizedGrantType("authorization_code")
                .authorizedGrantType("refresh_token")
                .authorizedGrantType("password")
                .scope("participant.profile")
                .scope("participant.email")
                .build());

        clients.add(Client.builder()
                .clientId("acme")
                .clientSecret("acmesecret")
                .authorizedGrantType("authorization_code")
                .authorizedGrantType("refresh_token")
                .authorizedGrantType("password")
                .scope("participant.profile")
                .build());

        /**
         * clients.inMemory()
         .withClient("acme")
         .secret("acmesecret")
         .authorizedGrantTypes("authorization_code", "refresh_token",
         "password").scopes("participant.profile").autoApprove(true);

         */

        repository.save(clients);

        return repository.findAll();

    }

}
