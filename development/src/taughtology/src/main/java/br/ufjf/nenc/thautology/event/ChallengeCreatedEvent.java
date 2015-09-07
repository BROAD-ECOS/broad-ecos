package br.ufjf.nenc.thautology.event;

import br.ufjf.nenc.thautology.model.Challenge;
import lombok.ToString;

@ToString
public class ChallengeCreatedEvent extends EntityCreatedEvent<Challenge>  {

    public ChallengeCreatedEvent(Challenge entity) {
        super(entity);
    }
}