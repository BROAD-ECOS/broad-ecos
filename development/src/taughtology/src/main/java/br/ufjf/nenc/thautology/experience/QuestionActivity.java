package br.ufjf.nenc.thautology.experience;

import br.ufjf.nenc.broadecos.experience.Activity;
import br.ufjf.nenc.broadecos.experience.Definition;
import br.ufjf.nenc.thautology.model.Question;
import com.google.common.collect.ImmutableMap;

import java.net.URI;

public class QuestionActivity {

    private final Question question;

    public QuestionActivity(Question question) {
        this.question = question;
    }

    public Activity toActivity() {
        Activity activity = new Activity();
        activity.setId(URI.create("http://dev.broadecos:8080/questions/" + question.getId()));
        activity.setDefinition(Definition.builder()
            .name(ImmutableMap.<String, String>builder().put("pt_BR", question.getTitle()).build())
            .description(ImmutableMap.<String, String>builder()
                            .put("pt_BR", question.getContent())
                            .build()
            )
            .moreInfo(URI.create("http://dev.broadecos:8080/#/question/" + question.getId()))
            .build()
        );
        return activity;
    }

}
