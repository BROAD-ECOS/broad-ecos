package br.ufjf.nenc.thautology.experience;

import br.ufjf.nenc.broadecos.experience.ExperienceStatement;
import br.ufjf.nenc.broadecos.experience.Verbs;
import br.ufjf.nenc.thautology.model.Answer;
import br.ufjf.nenc.thautology.model.AnswerAchievement;

public class AnswerStatement {

    private final Answer answer;

    public AnswerStatement(Answer answer) {
        this.answer = answer;
    }

    public ExperienceStatement toExperienceStatement(){
        return ExperienceStatement.builder()
                .actor(new UserActor(answer.getUser()).toActor())
                .verb(Verbs.ANSWERED.bean())
                .object(new QuestionActivity(answer.getQuestion()).toActivity())
                .result(new AnswerResult(answer).toResult())
                .authority(new TaughtologyAuthority().getAuthoriry())
                .build();
    }
}
