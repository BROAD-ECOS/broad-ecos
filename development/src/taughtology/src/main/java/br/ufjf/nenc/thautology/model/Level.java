package br.ufjf.nenc.thautology.model;

import com.google.common.collect.ImmutableMap;
import jdk.nashorn.internal.ir.annotations.Immutable;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

public enum Level implements Serializable {
    EASY("Easy", ImmutableMap.<Locale, String> builder().put(new Locale("pt","BR"), "Fácil").build()),
    MEDIUM("Medium", ImmutableMap.<Locale, String> builder().put(new Locale("pt","BR"), "Médio").build()),
    HARD("Hard", ImmutableMap.<Locale, String> builder().put(new Locale("pt","BR"), "Difícil").build()),
    INSANE("Insane", ImmutableMap.<Locale, String> builder().put(new Locale("pt","BR"), "Insano").build()),
    COMPLETE("Complete", ImmutableMap.<Locale, String> builder().put(new Locale("pt","BR"), "Completo").build());

    private static final long serialVersionUID = 1L;

    @Getter
    private final String name;
    private final Map<Locale, String> localizedNames;

    Level(String name, Map<Locale, String> localizedNames) {
        this.name = name;
        this.localizedNames=localizedNames;
    }

    public Level next() {
        Level nextLevel = COMPLETE;

        if (this != COMPLETE) {
            int nextLevelOrdinal = this.ordinal() + 1;
            nextLevel = Arrays.stream(values())
                    .filter(l -> l.ordinal() == nextLevelOrdinal)
                    .findFirst()
                    .get();
        }

        return nextLevel;
    }

    public String getLocalNameOrDefault(Locale locale){
        String localName = localizedNames.get(locale);
        if (localName == null) {
            localName = name;
        }
        return localName;
    }

}
