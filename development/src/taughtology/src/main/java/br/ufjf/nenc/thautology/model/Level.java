package br.ufjf.nenc.thautology.model;

import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;

public enum Level implements Serializable {
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard"),
    INSANE("Insane"),
    COMPLETE("Complete");

    private static final long serialVersionUID = 1L;

    @Getter
    private final String name;

    Level(String name) {
        this.name = name;
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

}
