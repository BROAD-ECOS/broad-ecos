package br.ufjf.nenc.thautology.rest;


import br.ufjf.nenc.thautology.service.QuestionService;
import br.ufjf.nenc.thautology.model.Question;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;

import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/questions")
@Log4j
public class AchievementResource {

    private final QuestionService questionService;

    @Autowired
    public AchievementResource(QuestionService questionService) {
        this.questionService = questionService;
    }

    @RequestMapping(method = GET)
    public Page<Question> getAll(){
        return questionService.findAll(new PageRequest(0, 10));
    }

    @RequestMapping(method = GET, value = "/random")
    public Question random(@QueryParam("participantId") String participantId){
        Optional<Question> question = questionService.randomByParticipant(participantId);
        return question.orElse(null);
    }

}
