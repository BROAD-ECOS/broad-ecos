package br.ufjf.nenc.thautology.experience;

import br.ufjf.nenc.broadecos.experience.Result;
import br.ufjf.nenc.thautology.model.Answer;

import static java.lang.String.format;

public class AnswerResult {
    private final Answer answer;

    public AnswerResult(Answer answer) {
        this.answer = answer;
    }

    public Result toResult() {
        return Result.builder()
                .completion(Boolean.TRUE)
                .success(answer.getCorrect())
                .response(buildResponse())
                .build();
    }

    private String buildResponse() {
        return format("%s respondeu ao desafio '%s' com a opção %s",
                answer.getUser(),
                answer.getQuestion().getTitle(),
                answer.getCorrect() ?"correta":"errada"
        );
    }
}
