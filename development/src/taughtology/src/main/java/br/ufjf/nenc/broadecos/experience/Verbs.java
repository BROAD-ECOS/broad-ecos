package br.ufjf.nenc.broadecos.experience;

import com.google.common.collect.ImmutableMap;
import lombok.ToString;

import java.net.URI;
import java.util.Map;

@ToString
public enum Verbs {
    ANSWERED(URI.create("http://wordnet-rdf.princeton.edu/wn31/200637941-v"),
            ImmutableMap.<String,String>builder()
                    .put("pt_BR", "Respondeu")
                    .put("en_US", "Answered")
                    .build()
    );

    private final URI id;

    private final Map<String, String> display;

    Verbs(URI id, Map<String, String> display) {
        this.id = id;
        this.display = display;
    }


    public URI getId() {
        return id;
    }

    public Map<String, String> getDisplay() {
        return display;
    }

    public Verb bean(){
        return new Verb(id, display);
    }
}