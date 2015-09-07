package br.ufjf.nenc.thautology.experience;

import br.ufjf.nenc.broadecos.experience.Result;
import br.ufjf.nenc.thautology.model.AnswerAchievement;

import static java.lang.String.format;

public class AnswerAchievementResult {

    private final AnswerAchievement achievement;

    public AnswerAchievementResult(AnswerAchievement achievement) {
        this.achievement = achievement;
    }

    public Result toResult() {
        return Result.builder()
                .score(new AnswerAchievementScore(achievement).toScore())
                .completion(Boolean.TRUE)
                .success(achievement.getAnswer().getCorrect())
                .response(buildResponse())
                .build();
    }

    private String buildResponse() {
        return format("%s rescebeu %d pontos por responder ao desafio %s com a opção %s",
            achievement.getUser(),
            achievement.getValue(),
            achievement.getAnswer().getQuestion().getTitle(),
            achievement.getAnswer().getCorrect()?"correta":"errada"
        );
    }
}
