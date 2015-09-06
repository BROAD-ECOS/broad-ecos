package br.ufjf.nenc.thautology.rest;


import br.ufjf.nenc.thautology.model.*;
import br.ufjf.nenc.thautology.service.ChallengeService;
import br.ufjf.nenc.thautology.util.IterableList;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/challenges")
@Log4j
public class ChallengeResource {


    private final ChallengeService challengeService;

    @Autowired
    public ChallengeResource(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }


    @RequestMapping(method = POST)
    public Reference<Challenge> post(@RequestBody @Valid Challenge challenge){

        log.info("Challenge received: " + challenge);

        Challenge savedChallenge = challengeService.save(challenge);

        return Reference.to(savedChallenge);

    }

    @RequestMapping(method = GET)
    public List<Challenge> getClassmates(@RequestParam("challenger") Optional<String> challengerId,
                                         @RequestParam("question") Optional<String> questionId){
        return new IterableList<>(challengeService.getChallenge(challengerId, questionId));
    }


}
