package br.ufjf.nenc.thautology.listener;

import br.ufjf.nenc.thautology.event.EntityCreatedEvent;
import br.ufjf.nenc.thautology.model.Achievement;
import lombok.extern.log4j.Log4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Log4j
@Component
public class BroadAchievementsListener {

    @EventListener
    public void listenAnswerCreated(EntityCreatedEvent<Achievement> achievementCreatedEvent) {
        log.info("Enviar para Moodle: " +achievementCreatedEvent);
    }

}
