package br.ufjf.nenc.broadecos.api;

import br.ufjf.nenc.broadecos.api.model.Course;
import br.ufjf.nenc.broadecos.api.model.PlatformInfo;
import lombok.Builder;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.CompletableFuture;

import static java.util.Collections.newSetFromMap;

@Builder
public class BroadEcosApi {

    private static final Set<OfflineContext> OFFLINE_CONTEXTS = newSetFromMap(new WeakHashMap<>());


    public BroadEcosApiContext withContext(Context context) {

        return BroadEcosApiContext.builder()
                .context(context)
                .build();
    }

    public CompletableFuture<BroadEcosApiContext> offlineContext(PlatformInfo platform, Course course) {

        return BroadEcosApiContext.builder()
                .context(context)
                .build();
    }

}
