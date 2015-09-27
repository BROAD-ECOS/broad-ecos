package br.ufjf.nenc.broadecos.rankr.component;

import br.ufjf.nenc.broadecos.api.BroadEcosApi;
import br.ufjf.nenc.broadecos.api.experience.*;
import br.ufjf.nenc.broadecos.api.model.ParticipantProfile;
import br.ufjf.nenc.broadecos.rankr.model.Conquest;
import br.ufjf.nenc.broadecos.rankr.model.User;
import br.ufjf.nenc.broadecos.rankr.service.ConquestService;
import br.ufjf.nenc.broadecos.rankr.service.UserService;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Component
public class ConquestLoader {

    private static final List<Verb> verbs = ImmutableList. <Verb> builder()
            .add(Verbs.WON.bean())
            .add(new Verb(URI.create("http://wordnet-rdf.princeton.edu/wn31/202294200-v"), null))
            .build();

    private final UserService userService;

    private final ConquestService conquestService;

    @Autowired
    public ConquestLoader(UserService userService, ConquestService conquestService) {
        this.userService = userService;
        this.conquestService = conquestService;
    }

    public void load(BroadEcosApi broadEcosApi) {

        verbs.forEach((verb) -> {
            ExperienceResponse response = broadEcosApi.getExperience(ExperienceRequest.builder()
                    .verbId(verb.getId())
                    .build()
            );

            response.getStatements().forEach((stmt) -> {

                String id = stmt.getActor().getAccount().getName();

                Optional<ParticipantProfile> participantProfile = broadEcosApi.getParticipant(id);

                if (participantProfile.isPresent()) {
                    User user = userService.retriveOrCreateUser(participantProfile.get());

                    Conquest conquest = new Conquest();
                    conquest.setStatement(stmt);

                    user.addPoints(conquest.getPoints());
                    conquest.setUser(user);

                    conquestService.save(conquest);
                }
            });
        });
    }

}
