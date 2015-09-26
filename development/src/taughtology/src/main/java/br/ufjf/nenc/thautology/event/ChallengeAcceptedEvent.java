package br.ufjf.nenc.thautology.event;

import br.ufjf.nenc.broadecos.api.Context;
import br.ufjf.nenc.thautology.model.Challenge;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class ChallengeAcceptedEvent {

    private final Challenge challenge;

    private final Context context;

    public ChallengeAcceptedEvent(Challenge challenge, Context context) {
        this.challenge = challenge;
        this.context = context;
    }
}
