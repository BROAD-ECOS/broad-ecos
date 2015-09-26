package br.ufjf.nenc.thautology.experience;

import br.ufjf.nenc.broadecos.api.experience.Score;
import br.ufjf.nenc.thautology.model.AnswerAchievement;
import br.ufjf.nenc.thautology.model.LevelPoints;

public class AnswerAchievementScore {

    private final AnswerAchievement achievement;

    public AnswerAchievementScore(AnswerAchievement achievement) {
        this.achievement = achievement;
    }

    public Score toScore() {
        Long value = LevelPoints.getPoints(achievement.getEntity().getQuestion().getLevel());
        return Score.builder()
                .max(Double.valueOf(value))
                .min(0d)
                .raw(Double.valueOf(achievement.getValue()))
                .build();
    }
}
