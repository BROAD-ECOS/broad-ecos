package br.ufjf.nenc.broadecos.api;

import br.ufjf.nenc.broadecos.api.model.Metadata;
import br.ufjf.nenc.broadecos.api.model.Scope;

public class Main {

    public static void main (String[] args) {

        /*final Metadata metadata = Metadata.builder()
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

        AuthRequest authRequest;
        try {
            authRequest  = new AuthRequest(metadata, "http://dev.broadecos/moodle/mod/broadecosmod/ws.php", "3", true);
        } catch (Exception e) {

        }

        System.out.println(authRequest);
        Context context = authRequest.createContext("code");

        System.out.println(broadEcosApi.withContext(context).getCurrentCourse());


    }*/
    }
}

