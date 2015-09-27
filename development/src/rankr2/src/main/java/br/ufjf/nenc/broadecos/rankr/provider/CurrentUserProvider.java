package br.ufjf.nenc.broadecos.rankr.provider;

import br.ufjf.nenc.broadecos.api.BroadEcosApi;
import br.ufjf.nenc.broadecos.api.Context;
import br.ufjf.nenc.broadecos.api.model.ParticipantProfile;
import br.ufjf.nenc.broadecos.rankr.model.CurrentUser;
import br.ufjf.nenc.broadecos.rankr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserProvider {

    @Autowired
    private final BroadEcosApi broadEcosApi;

    @Autowired
    private final UserService userService;

    @Autowired
    public CurrentUserProvider(BroadEcosApi broadEcosApi, UserService userService) {
        this.broadEcosApi = broadEcosApi;
        this.userService = userService;
    }

    public CurrentUser currentUser(Context context){

        ParticipantProfile profile = broadEcosApi
                .withContext(context)
                .getParticipant();

        profile.setPlatform(context.getPlatform());

        return new CurrentUser(userService.retriveOrCreateUser(profile), context);
    }

}
