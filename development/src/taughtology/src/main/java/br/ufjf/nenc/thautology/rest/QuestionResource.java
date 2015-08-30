package br.ufjf.nenc.thautology.rest;


import br.ufjf.nenc.thautology.model.CurrentUser;
import br.ufjf.nenc.thautology.model.Question;
import br.ufjf.nenc.thautology.service.QuestionService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/questions")
@Log4j
public class QuestionResource {

    private final QuestionService questionService;

    @Autowired
    public QuestionResource(QuestionService questionService) {
        this.questionService = questionService;
    }

    @RequestMapping(method = GET)
    public Page<Question> getAll(){
        return questionService.findAll(new PageRequest(0, 10));
    }

    @RequestMapping(method = GET, value = "/random")
    public Question random(CurrentUser currentUser){
        Optional<Question> question = questionService.nextQuestion(currentUser.getUser());
        return question.orElse(null);
    }

    @RequestMapping(method = GET, value = "/{id}")
    public Question get(@PathVariable("id") String questionId){
        Optional<Question> question = questionService.getQuestionById(questionId);
        return question.get();
    }
}
