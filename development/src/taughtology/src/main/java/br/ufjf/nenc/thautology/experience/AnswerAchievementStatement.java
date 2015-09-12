package br.ufjf.nenc.thautology.experience;

import br.ufjf.nenc.broadecos.experience.ExperienceStatement;
import br.ufjf.nenc.broadecos.experience.Verbs;
import br.ufjf.nenc.thautology.model.Achievement;
import br.ufjf.nenc.thautology.model.Answer;
import br.ufjf.nenc.thautology.model.AnswerAchievement;

public class AnswerAchievementStatement {

    private final AnswerAchievement achievement;

    public AnswerAchievementStatement(AnswerAchievement achievement) {
        this.achievement = achievement;
    }

    public ExperienceStatement toExperienceStatement(){
        ExperienceStatement statement = ExperienceStatement.builder()
                .actor(new UserActor(achievement.getUser()).toActor())
                .verb(Verbs.ANSWERED.bean())
                .object(new QuestionActivity(achievement.getEntity().getQuestion()).toActivity())
                .result(new AnswerAchievementResult(achievement).toResult())
                .build();

        return statement;
    }
}
