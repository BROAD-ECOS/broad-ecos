package br.ufjf.nenc.thautology.provider;

import br.ufjf.nenc.broadecos.BroadEcosApi;
import br.ufjf.nenc.broadecos.Context;
import br.ufjf.nenc.broadecos.model.ParticipantProfile;
import br.ufjf.nenc.thautology.model.CurrentUser;
import br.ufjf.nenc.thautology.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
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

    // @Cacheable(cacheResolver = "TOKEN_CACHE")
    public CurrentUser currentUser(Context context){

        ParticipantProfile profile = broadEcosApi
                .withContext(context)
                .getParticipant();

        return new CurrentUser(userService.retriveOrCreateUser(profile), context);
    }

}
