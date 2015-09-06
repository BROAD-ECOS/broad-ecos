package br.ufjf.nenc.thautology.service;

import br.ufjf.nenc.thautology.event.ChallengeCreatedEvent;
import br.ufjf.nenc.thautology.model.Challenge;
import br.ufjf.nenc.thautology.model.Comment;
import br.ufjf.nenc.thautology.model.Question;
import br.ufjf.nenc.thautology.model.User;
import br.ufjf.nenc.thautology.repository.ChallengeRepository;
import br.ufjf.nenc.thautology.repository.CommentRepository;
import br.ufjf.nenc.thautology.util.EntitySupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static java.util.Collections.singletonList;

@Service
public class ChallengeService {

    private final ChallengeRepository challengeRepository;

    private final UserService userService;

    private final QuestionService questionService;

    private final ApplicationEventPublisher publisher;

    @Autowired
    public ChallengeService(ChallengeRepository challengeRepository, UserService userService, QuestionService questionService, ApplicationEventPublisher publisher) {
        this.challengeRepository = challengeRepository;
        this.userService = userService;
        this.questionService = questionService;
        this.publisher = publisher;
    }

    public Challenge save(Challenge challenge) {
        challenge.setCreated(new Date());
        challenge.setLastUpdated(new Date());

        Challenge savedChallenge = challengeRepository.save(challenge);

        publisher.publishEvent(new ChallengeCreatedEvent(savedChallenge));

        return savedChallenge;
    }

    public Iterable<Challenge> getChallenge(Optional<String> challengerId, Optional<String> questionId) {
        Iterable<Challenge> challenges = Collections.emptyList();

        Optional<User> challenger = new EntitySupplier<>(challengerId, userService::getUser).supply();
        Optional<Question> question = new EntitySupplier<>(questionId, questionService::getQuestionById).supply();

        if (challenger.isPresent() &&  question.isPresent()) {
            Optional<Challenge> challenge = getChallenge(challenger.get(), question.get());
            if (challenge.isPresent()){
                challenges = singletonList(challenge.get());
            }
        }

        return challenges;
    }

    public Optional<Challenge> getChallenge(User challenger, Question question) {
        return challengeRepository.findOneByChallengerAndQuestion(challenger, question);
    }
}
