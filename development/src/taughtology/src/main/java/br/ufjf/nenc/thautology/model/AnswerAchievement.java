package br.ufjf.nenc.thautology.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection="achievement")
public class AnswerAchievement extends Achievement<Answer> {

    private Answer answer;

    @Override
    public Answer getEntity() {
        return answer;
    }
}
