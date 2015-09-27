package br.ufjf.nenc.broadecos.api;

import br.ufjf.nenc.broadecos.api.experience.ExperienceRequest;
import br.ufjf.nenc.broadecos.api.experience.ExperienceResponse;
import br.ufjf.nenc.broadecos.api.experience.ExperienceStatement;
import br.ufjf.nenc.broadecos.api.model.Metadata;
import br.ufjf.nenc.broadecos.api.model.ParticipantProfile;
import br.ufjf.nenc.broadecos.api.model.Scope;

import java.net.URI;
import java.util.List;
import java.util.Optional;

public class Main {

    public static void main (String[] args) {

       final Metadata metadata = Metadata.builder()
                .id("rankr")
                .name("Rankr")
                .description("Shows conquest informations.")
                .entryPoint("http://dev.broadecos:8090/#/index")
                .redirectURI("http://dev.broadecos:8090/auth/code")
                .scope(Scope.PARTICIPANT_PROFILE)
                .scope(Scope.COURSES_CURRENT)
                .scope(Scope.EXPERIENCES_READ)
                .scope(Scope.OFFLINE_ACCESS)
                .build();

        BroadEcosApiProvider broadEcosApi = BroadEcosApiProvider
                .builder()
                .metadata(metadata)
                .build();

        final Context context = new Context("2b0543999c653bb3e6fc90891500cfea","http://dev.broadecos/moodle/mod/broadecosmod/ws.php");


        BroadEcosApi api = broadEcosApi.withContext(context);

        Optional<ParticipantProfile> profile = api.getParticipant("3");

        System.out.print(profile);



        /*ExperienceResponse statements = api.getExperience(ExperienceRequest.builder()
                .verbId(URI.create("http://wordnet-rdf.princeton.edu/wn31/202294200-v"))
                .build()
        );


        System.out.print(statements);
        System.out.print(statements.getStatements().get(0).getStored());
        System.out.print(statements.getStatements().get(0).getTimestamp());*/

    }
}

