package br.ufjf.nenc.thautology.rest;

import br.ufjf.nenc.thautology.model.Answer;
import br.ufjf.nenc.thautology.model.Reference;
import br.ufjf.nenc.thautology.service.AnswerService;
import br.ufjf.nenc.thautology.util.IterableList;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping(value = "/answers")
@Log4j
public class AnswerResource {

    private final AnswerService answerService;

    @Autowired
    public AnswerResource(AnswerService answerService) {
        this.answerService = answerService;
    }

    @RequestMapping(method = POST)
    public Reference<Answer> post(@RequestBody @Valid Answer answer){

        log.info("Answer received: " + answer);

        Answer savedAnswer = answerService.save(answer);

        return Reference.to(savedAnswer);

    }

    @RequestMapping(path = "/{id}", method = GET)
    public Answer getById(@PathVariable("id") String id){
        log.info("Answer requested (id): " + id);

        return answerService.loadById(id);
    }


    @RequestMapping(method = GET)
    public List<Answer> get(@RequestParam("user") String userId){
        log.info("Answer requested by (userId): " + userId);

        return new IterableList<>(answerService.allByUserId(userId));
    }
}
