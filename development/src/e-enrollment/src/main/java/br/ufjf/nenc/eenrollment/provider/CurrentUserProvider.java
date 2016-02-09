package br.ufjf.nenc.eenrollment.provider;

import br.ufjf.nenc.broadecos.api.BroadEcosApiProvider;
import br.ufjf.nenc.broadecos.api.Context;
import br.ufjf.nenc.broadecos.api.model.ParticipantProfile;
import br.ufjf.nenc.eenrollment.model.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserProvider {

    @Autowired
    private final BroadEcosApiProvider broadEcosApiProvider;

    @Autowired
    public CurrentUserProvider(BroadEcosApiProvider broadEcosApiProvider) {
        this.broadEcosApiProvider = broadEcosApiProvider;
    }

    public CurrentUser currentUser(Context context){

        ParticipantProfile profile = broadEcosApiProvider
                .withContext(context)
                .getCurrentParticipant();

        profile.setPlatform(context.getPlatform());

        return new CurrentUser(profile, context);
    }

}
