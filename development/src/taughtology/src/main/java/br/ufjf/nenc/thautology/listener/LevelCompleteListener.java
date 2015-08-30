package br.ufjf.nenc.thautology.listener;

import br.ufjf.nenc.thautology.event.EntityCreatedEvent;
import br.ufjf.nenc.thautology.event.LevelCompleteEvent;
import br.ufjf.nenc.thautology.model.Answer;
import br.ufjf.nenc.thautology.service.AchievementService;
import br.ufjf.nenc.thautology.service.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Log4j
@Component
public class LevelCompleteListener {

    private final UserService userService;

    @Autowired
    public LevelCompleteListener(UserService userService) {
        this.userService = userService;
    }

    @EventListener
    public void listenLevelComplete(LevelCompleteEvent levelCompleteEvent) {
        log.info("Event received: "+levelCompleteEvent);
        userService.completeLevel(levelCompleteEvent.getUser());
    }

}
