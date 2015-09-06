package br.ufjf.nenc.thautology.listener;

import br.ufjf.nenc.thautology.event.ChallengeCreatedEvent;
import br.ufjf.nenc.thautology.model.Challenge;
import br.ufjf.nenc.thautology.model.Notification;
import br.ufjf.nenc.thautology.service.AchievementService;
import br.ufjf.nenc.thautology.service.NotificationService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Locale;

import static java.lang.String.format;

@Log4j
@Component
public class NotificationListener {

    private final String SUBJECT_MSG = "Você recebeu um desafio nível '%s' de %s ";

    private final String NOTIFICATION_MSG = "<p>Você recebeu o desafio '%s', de nível '%s' de %s. </p>" +
            "<p>Caso você acerte o desafio, ganha os pontos do desafio (%d), um ponto adicional, e premia seu desafiador com um ponto!</p>"+
            "<p>Para começar va ao seu painel e aceite o desafio.</p>"+
            "<p><strong>Boa sorte!</strong></p>";

    private final NotificationService notificationService;

    private final AchievementService achievementService;

    private final Locale locale;

    @Autowired
    public NotificationListener(NotificationService notificationService, AchievementService achievementService, Locale locale) {
        this.notificationService = notificationService;
        this.achievementService = achievementService;
        this.locale = locale;
    }

    @EventListener
    public void challengeSentListener(ChallengeCreatedEvent challengeCreatedEvent) {
        log.info("Event received: " + challengeCreatedEvent);

        Challenge challenge = challengeCreatedEvent.getEntity();

        Notification challengeNotification = Notification.builder()
                .subject(buildSubject(challenge))
                .message(buildMessage(challenge))
                .to(challenge.getChallenged())
                .seen(Boolean.FALSE)
                .build();

        log.info("Generated Notification " + challengeNotification);

        notificationService.save(challengeNotification);
    }

    private String buildSubject(Challenge challenge) {
        return format(SUBJECT_MSG,
                challenge.getQuestion().getLevelName(locale),
                challenge.getChallenger().getFullName()
        );
    }

    private String buildMessage(Challenge challenge) {
        return format(NOTIFICATION_MSG,
                challenge.getQuestion().getTitle(),
                challenge.getQuestion().getLevelName(locale),
                challenge.getChallenger().getFullName(),
                achievementService.getPoints(challenge.getQuestion().getLevel())
        );
    }
}
