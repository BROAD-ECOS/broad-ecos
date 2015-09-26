package br.ufjf.nenc.thautology.experience;

import br.ufjf.nenc.broadecos.api.Context;
import br.ufjf.nenc.broadecos.api.experience.Object;

public class TaughtologyContext {

    private final Context context;

    public TaughtologyContext(Context context) {
        this.context = context;
    }


    public br.ufjf.nenc.broadecos.api.experience.Context toContext() {
        return br.ufjf.nenc.broadecos.api.experience.Context.builder()
                .platform(context.getPlatform())
                .build();
    }
}
