package br.ufjf.nenc.thautology.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection="achievement")
public class ChallengeAchievement extends Achievement<Challenge> {

    private Challenge challenge;

    @Override
    public Challenge getEntity() {
        return challenge;
    }
}
