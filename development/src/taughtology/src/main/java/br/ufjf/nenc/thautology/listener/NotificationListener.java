package br.ufjf.nenc.thautology.listener;

import br.ufjf.nenc.thautology.event.ChallengeAcceptedEvent;
import br.ufjf.nenc.thautology.event.ChallengeCreatedEvent;
import br.ufjf.nenc.thautology.event.ChallengeMetEvent;
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

    private final String CHALLENGE_SENT_SBJ = "Você recebeu um desafio nível '%s' de %s ";

    private final String CHALLENGE_SENT_MSG = "<p>Você recebeu o desafio '%s', de nível '%s' de %s. </p>" +
            "<p>Caso você acerte o desafio, ganha os pontos do desafio (%d), um ponto adicional, e premia seu desafiador com um ponto!</p>"+
            "<p>Para começar va ao seu painel e aceite o desafio.</p>"+
            "<p><strong>Boa sorte!</strong></p>";

    private final String CHALLENGE_MET_SBJ = "%s aceitou o desafio %s!";

    private final String CHALLENGE_MET_SUCCESS_MSG = "<p>%s aceitou e conseguiu vencer o desafio '%s' de nível '%s' enviado</p>" +
            "<p>Você ganhou <strong>%d pontos</strong></p>"+
            "<p><strong>Muito bem!</strong></p>";

    private final String CHALLENGE_MET_FAILURE_MSG = "<p>%s aceitou o desafio '%s' de nível '%s', mas não conseguiu vencê-lo</p>" +
            "<p>Você ganhou <strong>%d pontos</strong></p>"+
            "<p><strong>Melhor sorte na próxima</strong>...</p>";

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
    public void challengeAcceptedListener(ChallengeAcceptedEvent challengeAcceptedEvent) {
        log.info("Event received: " + challengeAcceptedEvent);

        Challenge challenge = challengeAcceptedEvent.getChallenge();

        Notification challengeNotification = Notification.builder()
                .subject(buildChallengAcceptedSubject(challenge))
                .message(buildChallengAcceptedMessage(challenge))
                .to(challenge.getChallenger())
                .seen(Boolean.FALSE)
                .build();

        log.info("Generated Notification " + challengeNotification);

        notificationService.save(challengeNotification);
    }

    private String buildChallengAcceptedSubject(Challenge challenge) {
        return format(CHALLENGE_MET_SBJ,
                challenge.getChallenger().getFullName(),
                challenge.getQuestion().getTitle()

        );
    }

    private String buildChallengAcceptedMessage(Challenge challenge) {

        final String message = challenge.getMet() ?  CHALLENGE_MET_SUCCESS_MSG : CHALLENGE_MET_FAILURE_MSG;

        return format(message,
                challenge.getChallenged().getFullName(),
                challenge.getQuestion().getTitle(),
                challenge.getQuestion().getLevelName(locale),
                achievementService.calculatePoints(challenge)
        );
    }


    @EventListener
    public void challengeSentListener(ChallengeCreatedEvent challengeCreatedEvent) {
        log.info("Event received: " + challengeCreatedEvent);

        Challenge challenge = challengeCreatedEvent.getEntity();

        Notification challengeNotification = Notification.builder()
                .subject(buildChallengeSentSubject(challenge))
                .message(buildhallengeSentMessage(challenge))
                .to(challenge.getChallenged())
                .seen(Boolean.FALSE)
                .build();

        log.info("Generated Notification " + challengeNotification);

        notificationService.save(challengeNotification);
    }

    private String buildChallengeSentSubject(Challenge challenge) {
        return format(CHALLENGE_SENT_SBJ,
                challenge.getQuestion().getLevelName(locale),
                challenge.getChallenger().getFullName()
        );
    }

    private String buildhallengeSentMessage(Challenge challenge) {
        return format(CHALLENGE_SENT_MSG,
                challenge.getQuestion().getTitle(),
                challenge.getQuestion().getLevelName(locale),
                challenge.getChallenger().getFullName(),
                achievementService.getPoints(challenge.getQuestion().getLevel())
        );
    }
}
