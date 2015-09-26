package br.ufjf.nenc.thautology.experience;

import br.ufjf.nenc.broadecos.api.experience.ExperienceStatement;
import br.ufjf.nenc.broadecos.api.experience.Verbs;
import br.ufjf.nenc.thautology.model.ChallengeAchievement;

import static com.google.common.base.Preconditions.checkArgument;

public class ChallengeAchievementStatement {

    private final ChallengeAchievement achievement;

    public ChallengeAchievementStatement(ChallengeAchievement achievement) {
        checkArgument(achievement != null);
        checkArgument(achievement.getChallenge().getMet());

        this.achievement = achievement;
    }

    public ExperienceStatement toExperienceStatement(){
        return ExperienceStatement.builder()
                .actor(new UserActor(achievement.getUser()).toActor())
                .verb(Verbs.WON.bean())
                .object(new QuestionActivity(achievement.getEntity().getQuestion()).toActivity())
                .result(new ChallengeAchievementResult(achievement).toResult())
                .authority(new TaughtologyAuthority().getAuthoriry())
                .build();
    }
}
