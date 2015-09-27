package br.ufjf.nenc.thautology.provider;

import br.ufjf.nenc.broadecos.api.BroadEcosApi;
import br.ufjf.nenc.broadecos.api.BroadEcosApiProvider;
import br.ufjf.nenc.broadecos.api.Context;
import br.ufjf.nenc.broadecos.api.model.ParticipantProfile;
import br.ufjf.nenc.thautology.model.CurrentUser;
import br.ufjf.nenc.thautology.service.UserService;
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

    // @Cacheable(cacheResolver = "TOKEN_CACHE")
    public CurrentUser currentUser(Context context){

        ParticipantProfile profile = broadEcosApiProvider
                .withContext(context)
                .getParticipant();

        profile.setPlatform(context.getPlatform());

        return new CurrentUser(userService.retriveOrCreateUser(profile), context);
    }

}
