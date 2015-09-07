package br.ufjf.nenc.thautology.event;

import br.ufjf.nenc.broadecos.Context;
import br.ufjf.nenc.thautology.model.Answer;


public class AnswerCreatedEvent extends EntityCreatedEvent<Answer>  {

    public AnswerCreatedEvent(Answer entity, Context context) {
        super(entity, context);
    }
}
