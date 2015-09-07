package br.ufjf.nenc.thautology.service;

import br.ufjf.nenc.thautology.event.ChallengeAcceptedEvent;
import br.ufjf.nenc.thautology.event.ChallengeCreatedEvent;
import br.ufjf.nenc.thautology.event.ChallengeMetEvent;
import br.ufjf.nenc.thautology.model.Challenge;
import br.ufjf.nenc.thautology.model.Question;
import br.ufjf.nenc.thautology.model.User;
import br.ufjf.nenc.thautology.repository.ChallengeRepository;
import br.ufjf.nenc.thautology.util.EntitySupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
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
        checkArgument(challenge != null);

        final Challenge savedChallenge;
        if (challenge.getId()==null) {
           savedChallenge = insert(challenge);
        } else {
           savedChallenge = update(challenge);
        }

        return savedChallenge;
    }

    private Challenge update(Challenge challenge) {
        checkArgument(challenge != null);
        checkArgument(challenge.getId()!=null);

        challenge.setLastUpdated(new Date());

        Challenge previousChallenge =  challengeRepository.findOne(challenge.getId());

        Challenge savedChallenge = challengeRepository.save(challenge);

        if (!previousChallenge.getMet().equals(savedChallenge.getMet()) && savedChallenge.getMet()) {
            publisher.publishEvent(new ChallengeMetEvent(savedChallenge));
        }

        if (!previousChallenge.getAccepted().equals(savedChallenge.getAccepted()) && savedChallenge.getAccepted()) {
            publisher.publishEvent(new ChallengeAcceptedEvent(savedChallenge));
        }

        return challengeRepository.save(savedChallenge);
    }

    private Challenge insert(Challenge challenge) {
        checkArgument(challenge != null);
        final Date now = new Date();
        challenge.setCreated(now);
        challenge.setLastUpdated(now);

        Challenge savedChallenge = challengeRepository.save(challenge);

        publisher.publishEvent(new ChallengeCreatedEvent(savedChallenge));

        return savedChallenge;
    }

    public Iterable<Challenge> getChallenges(Optional<String> challengerId, Optional<String> challengedId, Optional<String> questionId, Optional<Boolean> accepted) {
        Iterable<Challenge> challenges = Collections.emptyList();

        if (challengerId.isPresent()) {
            Optional<User> challenger = new EntitySupplier<>(challengerId, userService::getUser).supply();
            if (challenger.isPresent()) {
                if (questionId.isPresent()) {
                    Optional<Question> question = new EntitySupplier<>(questionId, questionService::getQuestionById).supply();
                    Optional<Challenge> challenge = getChallenge(challenger.get(), question.get());
                    if (challenge.isPresent()) {
                        challenges = singletonList(challenge.get());
                    }
                } else {
                    challenges = getFromChallenger(challenger.get());
                }
            }
        }

        if (challengedId.isPresent()) {
            Optional<User> challenged = new EntitySupplier<>(challengedId, userService::getUser).supply();
            if (challenged.isPresent()) {
                if (accepted.isPresent()) {
                    challenges = getUserAcceptedChallenges(challenged.get(), accepted.get());
                } else {
                    challenges = getReceivedChallenges(challenged.get());
                }
            }
        }

        return challenges;
    }

    private Iterable<Challenge> getReceivedChallenges(User user) {
        return challengeRepository.findAllByChallenged(user);
    }

    private Iterable<Challenge> getFromChallenger(User user) {
        return challengeRepository.findAllByChallenger(user);
    }

    private Iterable<Challenge> getUserAcceptedChallenges(User user, Boolean accepted) {
        return challengeRepository.findAllByChallengedAndAccepted(user, accepted);
    }

    public Optional<Challenge> getChallenge(User challenger, Question question) {
        return challengeRepository.findOneByChallengerAndQuestion(challenger, question);
    }

    public Optional<Challenge> getChallenge(String challengeId) {
        checkArgument(challengeId != null);
        return Optional.ofNullable(challengeRepository.findOne(challengeId));
    }
}
