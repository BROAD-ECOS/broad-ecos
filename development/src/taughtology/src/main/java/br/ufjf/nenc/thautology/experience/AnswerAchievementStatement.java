package br.ufjf.nenc.thautology.experience;

import br.ufjf.nenc.broadecos.api.experience.ExperienceStatement;
import br.ufjf.nenc.broadecos.api.experience.Verbs;
import br.ufjf.nenc.thautology.model.AnswerAchievement;

import static com.google.common.base.Preconditions.*;

public class AnswerAchievementStatement {

    private final AnswerAchievement achievement;

    public AnswerAchievementStatement(AnswerAchievement achievement) {
        checkArgument(achievement != null);
        checkArgument(achievement.getAnswer().getCorrect());
        this.achievement = achievement;
    }

    public ExperienceStatement toExperienceStatement(){
        return ExperienceStatement.builder()
                .actor(new UserActor(achievement.getUser()).toActor())
                .verb(Verbs.WON.bean())
                .object(new QuestionActivity(achievement.getEntity().getQuestion()).toActivity())
                .result(new AnswerAchievementResult(achievement).toResult())
                .authority(new TaughtologyAuthority().getAuthoriry())
                .build();
    }
}
