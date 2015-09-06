package br.ufjf.nenc.thautology.service;

import br.ufjf.nenc.thautology.model.Challenge;
import br.ufjf.nenc.thautology.model.Comment;
import br.ufjf.nenc.thautology.repository.ChallengeRepository;
import br.ufjf.nenc.thautology.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ChallengeService {

    private final ChallengeRepository challengeRepository;

    @Autowired
    public ChallengeService(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    public Challenge save(Challenge challenge) {
        challenge.setCreated(new Date());
        challenge.setLastUpdated(new Date());

        return challengeRepository.save(challenge);
    }

}
