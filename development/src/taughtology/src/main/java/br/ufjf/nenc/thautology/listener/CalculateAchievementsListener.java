package br.ufjf.nenc.thautology.listener;

import br.ufjf.nenc.thautology.event.ChallengeMetEvent;
import br.ufjf.nenc.thautology.event.EntityCreatedEvent;
import br.ufjf.nenc.thautology.model.Answer;
import br.ufjf.nenc.thautology.model.Challenge;
import br.ufjf.nenc.thautology.service.AchievementService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Log4j
@Component
public class CalculateAchievementsListener  {

    private final AchievementService achievementService;

    @Autowired
    public CalculateAchievementsListener(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    @EventListener
    public void listenAnswerCreated(EntityCreatedEvent<Answer> answerCreationEvent) {
        log.info("Event received: "+answerCreationEvent);
        achievementService.calculateAchievements(answerCreationEvent.getEntity());
    }


    @EventListener
    public void listenChallendeMet(ChallengeMetEvent challengeMetEvent) {
        log.info("Event received: "+challengeMetEvent);
        achievementService.calculateAchievements(challengeMetEvent.getChallenge());
    }

}
