package br.ufjf.nenc.thautology.event;

import br.ufjf.nenc.broadecos.api.Context;
import br.ufjf.nenc.thautology.model.Challenge;
import lombok.ToString;

@ToString
public class ChallengeCreatedEvent extends EntityCreatedEvent<Challenge>  {

    public ChallengeCreatedEvent(Challenge entity, Context context) {
        super(entity, context);
    }
}
