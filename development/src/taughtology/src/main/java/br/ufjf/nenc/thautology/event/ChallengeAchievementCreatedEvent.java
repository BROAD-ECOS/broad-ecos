package br.ufjf.nenc.thautology.event;


import br.ufjf.nenc.broadecos.Context;
import br.ufjf.nenc.thautology.model.ChallengeAchievement;

public class ChallengeAchievementCreatedEvent extends EntityCreatedEvent<ChallengeAchievement>  {

    public ChallengeAchievementCreatedEvent(ChallengeAchievement entity, Context context) {
        super(entity, context);
    }
}
