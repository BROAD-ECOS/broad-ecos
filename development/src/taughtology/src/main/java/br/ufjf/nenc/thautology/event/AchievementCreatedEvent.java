package br.ufjf.nenc.thautology.event;


import br.ufjf.nenc.thautology.model.Achievement;

public class AchievementCreatedEvent extends EntityCreatedEvent<Achievement>  {

    public AchievementCreatedEvent(Achievement entity) {
        super(entity);
    }
}
