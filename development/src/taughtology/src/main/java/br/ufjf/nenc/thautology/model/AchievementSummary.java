package br.ufjf.nenc.thautology.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@EqualsAndHashCode
public class AchievementSummary {

    private Long totalPoints = 0l;

    private Long questionsAnswered = 0l;

    private Long correctAnswers = 0l;

    private Long acceptedChallenges = 0l;

    private Long sentChallenges = 0l;

}
