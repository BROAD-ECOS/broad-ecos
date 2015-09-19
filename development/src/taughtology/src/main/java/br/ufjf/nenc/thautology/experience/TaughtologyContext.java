package br.ufjf.nenc.thautology.experience;

import br.ufjf.nenc.broadecos.Context;
import br.ufjf.nenc.broadecos.experience.*;
import br.ufjf.nenc.broadecos.experience.Object;
import com.google.common.collect.ImmutableMap;

public class TaughtologyContext {

    private final Context context;

    public TaughtologyContext(Context context) {
        this.context = context;
    }


    public br.ufjf.nenc.broadecos.experience.Context toContext() {
        return br.ufjf.nenc.broadecos.experience.Context.builder()
                .platform(context.getPlatform())
                .build();
    }
}
