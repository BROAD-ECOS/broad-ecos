package br.ufjf.nenc.thautology.service;

import br.ufjf.nenc.broadecos.BroadEcosApi;
import br.ufjf.nenc.broadecos.Context;
import br.ufjf.nenc.broadecos.experience.ExperienceStatement;
import br.ufjf.nenc.broadecos.model.Reference;
import br.ufjf.nenc.thautology.experience.AnswerAchievementStatement;
import br.ufjf.nenc.thautology.experience.AnswerStatement;
import br.ufjf.nenc.thautology.experience.ChallengeAchievementStatement;
import br.ufjf.nenc.thautology.model.Answer;
import br.ufjf.nenc.thautology.model.AnswerAchievement;
import br.ufjf.nenc.thautology.model.ChallengeAchievement;
import com.google.common.base.Preconditions;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j
@Service
public class ExperienceService {

    private final BroadEcosApi broadEcosApi;

    @Autowired
    public ExperienceService(BroadEcosApi broadEcosApi) {
        this.broadEcosApi = broadEcosApi;
    }

    public Optional<Reference> sendAchievementExperienced(AnswerAchievement achievement, Context context) {
        Preconditions.checkArgument(context!=null);
        Preconditions.checkArgument(achievement!=null);
        Preconditions.checkArgument(achievement.getAnswer()!=null);

        Optional<Reference> reference = Optional.empty();
        if (achievement.getAnswer().getCorrect()) {
            AnswerAchievementStatement achievementStatement = new AnswerAchievementStatement(achievement);
            reference = Optional.of(send(achievementStatement.toExperienceStatement(), context));
        }

        return reference;
    }

    public Reference sendAnsweredExperience(Answer answer, Context context) {
        Preconditions.checkArgument(context!=null);
        Preconditions.checkArgument(answer!=null);

        AnswerStatement achievementStatement = new AnswerStatement(answer);
        return send(achievementStatement.toExperienceStatement(), context);
    }

    public Optional<Reference> sendChallengeAchievementExperienced(ChallengeAchievement challengeAchievement, Context context) {
        Preconditions.checkArgument(context!=null);
        Preconditions.checkArgument(challengeAchievement!=null);

        Optional<Reference> reference = Optional.empty();

        if (challengeAchievement.getChallenge().getMet()) {
            ChallengeAchievementStatement challengeAchievementStatement = new ChallengeAchievementStatement(challengeAchievement);
            reference = Optional.of(send(challengeAchievementStatement.toExperienceStatement(), context));
        }

        return reference;
    }

    public Reference send(ExperienceStatement statement, Context context) {
        log.info("Experience Statement: [" + statement + "], context: [" + context + "]");
        return broadEcosApi.withContext(context)
                .sendExperience(statement);
    }
}
