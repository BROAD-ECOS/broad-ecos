package br.ufjf.nenc.thautology.event;

import br.ufjf.nenc.thautology.model.Answer;


public class AnswerCreatedEvent extends EntityCreatedEvent<Answer>  {

    public AnswerCreatedEvent(Answer entity) {
        super(entity);
    }
}
