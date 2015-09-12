package br.ufjf.nenc.thautology.model;

import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Comparator;

@ToString
public enum LevelPoints {
    EASY(Level.EASY, 5l),
    MEDIUM(Level.MEDIUM, 10l),
    HARD(Level.HARD, 25l),
    INSANE(Level.INSANE, 100l);

    public static final Comparator<LevelPoints> COMPARATOR = (c1, c2) -> c1.getPoints().compareTo(c2.getPoints());


    @Getter private final Long points;
    @Getter private final Level level;

    LevelPoints(Level level, Long points) {
        this.level = level;
        this.points = points;
    }

    public static Long getPoints(Level level) {
        for(LevelPoints levelPoints : values()) {
            if (level == levelPoints.level) {
                return levelPoints.points;
            }
        }
        throw new IllegalStateException("Unknow points for level "+level);
    }

    public static Long min() {
        return Arrays.stream(values())
                .min(COMPARATOR)
                .get()
                .getPoints();
    }


    public static Long max() {
        return Arrays.stream(values())
                .max(COMPARATOR)
                .get()
                .getPoints();
    }
}
