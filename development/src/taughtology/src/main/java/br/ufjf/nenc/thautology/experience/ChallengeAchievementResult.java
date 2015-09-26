package br.ufjf.nenc.thautology.experience;

import br.ufjf.nenc.broadecos.api.experience.Result;
import br.ufjf.nenc.thautology.model.ChallengeAchievement;

import static java.lang.String.format;

public class ChallengeAchievementResult {

    private final ChallengeAchievement achievement;

    public ChallengeAchievementResult(ChallengeAchievement achievement) {
        this.achievement = achievement;
    }

    public Result toResult() {
        return Result.builder()
                .score(new ChallengeAchievementScore(achievement).toScore())
                .completion(Boolean.TRUE)
                .success(achievement.getChallenge().getMet())
                .response(buildResponse())
                .build();
    }

    private String buildResponse() {
        final String response;
        if (achievement.isChallenger()) {
            response = buildChallengerResponse();
        } else {
            response = buildChallengedResponse();
        }
        return response;
    }

    private String buildChallengerResponse() {
        return format("%s recebeu %d pontos pelo desafio %s enviado para %s, que respondeu corretamente.",
                achievement.getUser(),
                achievement.getValue(),
                achievement.getChallenge().getQuestion().getTitle(),
                achievement.getChallenge().getChallenged().getFullName()
        );
    }

    private String buildChallengedResponse() {
        return format("%s rescebeu %d ponto(s) extra(s) por responder ao desafio %s, enviado por %s, corretamente",
                achievement.getUser(),
                achievement.getValue(),
                achievement.getChallenge().getQuestion().getTitle(),
                achievement.getChallenge().getChallenger().getFullName()
        );
    }
}
