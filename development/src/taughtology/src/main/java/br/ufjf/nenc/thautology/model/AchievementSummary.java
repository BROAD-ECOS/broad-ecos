package br.ufjf.nenc.thautology.model;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AchievementSummary {

    private Long totalPoints = 0l;

    private Long questionsAnswered = 0l;

    private Long correctAnswers = 0l;

    private Long acceptedChallenges = 0l;

    private Long sentChallenges = 0l;

}
