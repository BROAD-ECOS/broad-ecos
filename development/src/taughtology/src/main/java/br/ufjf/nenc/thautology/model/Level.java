package br.ufjf.nenc.thautology.model;

import com.google.common.collect.ImmutableMap;
import jdk.nashorn.internal.ir.annotations.Immutable;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

public enum Level implements Serializable {
    EASY("Easy", ImmutableMap.<String, String> builder().put("pt_BR", "Fácil").build()),
    MEDIUM("Medium", ImmutableMap.<String, String> builder().put("pt_BR", "Médio").build()),
    HARD("Hard", ImmutableMap.<String, String> builder().put("pt_BR", "Difícil").build()),
    INSANE("Insane", ImmutableMap.<String, String> builder().put("pt_BR", "Insano").build()),
    COMPLETE("Complete", ImmutableMap.<String, String> builder().put("pt_BR", "Completo").build());

    private static final long serialVersionUID = 1L;

    @Getter
    private final String name;
    private final Map<String, String> localizedNames;

    Level(String name, Map<String, String> localizedNames) {
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
        String localName = localizedNames.get(locale.toString());
        if (localName == null) {
            localName = name;
        }
        return localName;
    }

}
