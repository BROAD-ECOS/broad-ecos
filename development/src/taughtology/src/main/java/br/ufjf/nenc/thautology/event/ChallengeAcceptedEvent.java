package br.ufjf.nenc.thautology.event;

import br.ufjf.nenc.thautology.model.Challenge;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

@Getter
@ToString
@EqualsAndHashCode
public class ChallengeAcceptedEvent {

    private final Challenge challenge;

    public ChallengeAcceptedEvent(Challenge challenge) {
        this.challenge = challenge;
    }
}
