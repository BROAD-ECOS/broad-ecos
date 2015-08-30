package br.ufjf.nenc.broadecos;

import lombok.Builder;

@Builder
public class BroadEcosApi {

    public BroadEcosApiContext withContext(Context context) {

        return BroadEcosApiContext.builder()
                .context(context)
                .build();
    }
}
