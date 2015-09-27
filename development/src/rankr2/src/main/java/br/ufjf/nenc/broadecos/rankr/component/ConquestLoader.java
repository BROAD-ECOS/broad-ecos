package br.ufjf.nenc.broadecos.rankr.component;

import br.ufjf.nenc.broadecos.api.BroadEcosApi;
import br.ufjf.nenc.broadecos.api.BroadEcosApiProvider;
import br.ufjf.nenc.broadecos.api.Context;
import br.ufjf.nenc.broadecos.api.experience.Verb;
import br.ufjf.nenc.broadecos.api.experience.Verbs;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConquestLoader {

    private static final List<Verbs> verbs = ImmutableList. <Verbs> builder()
            .add(Verbs.WON)
            .build();

    public void load(BroadEcosApi broadEcosApi) {

        broadEcosApi.getExperience();

    }

}
