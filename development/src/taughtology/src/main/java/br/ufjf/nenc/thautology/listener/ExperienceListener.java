package br.ufjf.nenc.thautology.listener;

import br.ufjf.nenc.thautology.event.EntityCreatedEvent;
import br.ufjf.nenc.thautology.model.Achievement;
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
    public void listenAnswerCreated(EntityCreatedEvent<AnswerAchievement> answerCreationEvent) {
        log.info("Experience event received "+answerCreationEvent);
        experienceService.sendAchivementExperienced(answerCreationEvent.getEntity(), answerCreationEvent.getContext());
    }

}
