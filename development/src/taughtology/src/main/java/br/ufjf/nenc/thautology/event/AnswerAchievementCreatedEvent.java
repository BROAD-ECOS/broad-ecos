package br.ufjf.nenc.thautology.event;


import br.ufjf.nenc.broadecos.Context;
import br.ufjf.nenc.thautology.model.AnswerAchievement;

public class AnswerAchievementCreatedEvent extends EntityCreatedEvent<AnswerAchievement>  {

    public AnswerAchievementCreatedEvent(AnswerAchievement entity, Context context) {
        super(entity, context);
    }
}
