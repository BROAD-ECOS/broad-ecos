package br.ufjf.nenc.thautology.service;

import br.ufjf.nenc.broadecos.BroadEcosApi;
import br.ufjf.nenc.broadecos.Context;
import br.ufjf.nenc.broadecos.experience.ExperienceStatement;
import br.ufjf.nenc.broadecos.model.Reference;
import br.ufjf.nenc.thautology.experience.AnswerAchievementStatement;
import br.ufjf.nenc.thautology.model.Achievement;
import br.ufjf.nenc.thautology.model.AnswerAchievement;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j
@Service
public class ExperienceService {

    private final BroadEcosApi broadEcosApi;

    @Autowired
    public ExperienceService(BroadEcosApi broadEcosApi) {
        this.broadEcosApi = broadEcosApi;
    }

    public Reference sendAchivementExperienced(AnswerAchievement achievement, Context context) {
        AnswerAchievementStatement achievementStatement = new AnswerAchievementStatement(achievement);
        return send(achievementStatement.toExperienceStatement(), context);
    }

    public Reference send(ExperienceStatement statement, Context context) {
        log.info("Experience Statement: ["+statement+"], context: ["+context+"]");
        return broadEcosApi.withContext(context)
                .sendExperience(statement);
    }
}
