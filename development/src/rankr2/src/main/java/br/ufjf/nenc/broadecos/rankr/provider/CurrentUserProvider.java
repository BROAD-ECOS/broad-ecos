package br.ufjf.nenc.broadecos.rankr.provider;

import br.ufjf.nenc.broadecos.api.BroadEcosApi;
import br.ufjf.nenc.broadecos.api.BroadEcosApiProvider;
import br.ufjf.nenc.broadecos.api.Context;
import br.ufjf.nenc.broadecos.api.model.ParticipantProfile;
import br.ufjf.nenc.broadecos.rankr.model.CurrentUser;
import br.ufjf.nenc.broadecos.rankr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserProvider {

    @Autowired
    private final BroadEcosApiProvider broadEcosApiProvider;

    @Autowired
    private final UserService userService;

    @Autowired
    public CurrentUserProvider(BroadEcosApiProvider broadEcosApiProvider, UserService userService) {
        this.broadEcosApiProvider = broadEcosApiProvider;
        this.userService = userService;
    }

    public CurrentUser currentUser(Context context){

        ParticipantProfile profile = broadEcosApiProvider
                .withContext(context)
                .getParticipant();

        profile.setPlatform(context.getPlatform());

        return new CurrentUser(userService.retriveOrCreateUser(profile), context);
    }

}
