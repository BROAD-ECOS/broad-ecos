package br.ufjf.nenc.thautology.listener;

import br.ufjf.nenc.thautology.event.ChallengeAchievementCreatedEvent;
import br.ufjf.nenc.thautology.event.EntityCreatedEvent;
import br.ufjf.nenc.thautology.event.LevelCompleteEvent;
import br.ufjf.nenc.thautology.model.Answer;
import br.ufjf.nenc.thautology.model.AnswerAchievement;
import br.ufjf.nenc.thautology.service.ExperienceService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Log4j
@Component
public class ExperienceListener {

    private final ExperienceService experienceService;

    @Autowired
    public ExperienceListener(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @EventListener
    public void listenAnswerAchievementCreated(EntityCreatedEvent<AnswerAchievement> answerCreationEvent) {
        log.info("Answer achievement experience event received "+answerCreationEvent);
        experienceService.sendAchievementExperienced(answerCreationEvent.getEntity(), answerCreationEvent.getContext());
    }

    @EventListener
    public void listenAnswerCreated(EntityCreatedEvent<Answer> answerCreationEvent) {
        log.info("Answer experience event received "+answerCreationEvent);
        experienceService.sendAnsweredExperience(answerCreationEvent.getEntity(), answerCreationEvent.getContext());
    }

    @EventListener
    public void listenChallengeAchievementCreated(ChallengeAchievementCreatedEvent challengeAchievementCreatedEvent) {
        log.info("Challenge achievement experience event received "+challengeAchievementCreatedEvent);
        experienceService.sendChallengeAchievementExperienced(challengeAchievementCreatedEvent.getEntity(), challengeAchievementCreatedEvent.getContext());
    }
}
