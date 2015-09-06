package br.ufjf.nenc.thautology.rest;


import br.ufjf.nenc.thautology.model.Challenge;
import br.ufjf.nenc.thautology.model.Comment;
import br.ufjf.nenc.thautology.model.Reference;
import br.ufjf.nenc.thautology.service.ChallengeService;
import br.ufjf.nenc.thautology.service.CommentService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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

        return Reference.from(savedChallenge);

    }

}
