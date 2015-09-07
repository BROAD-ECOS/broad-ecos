package br.ufjf.nenc.thautology.service;

import br.ufjf.nenc.thautology.component.BroadContext;
import br.ufjf.nenc.thautology.event.AnswerAchievementCreatedEvent;
import br.ufjf.nenc.thautology.event.ChallengeAchievementCreatedEvent;
import br.ufjf.nenc.thautology.model.*;
import br.ufjf.nenc.thautology.repository.AchievementRepository;
import br.ufjf.nenc.thautology.repository.AnswerAchievementRepository;
import br.ufjf.nenc.thautology.util.EntitySupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

@Service
public class AchievementService {

    private final Long CHALLENGE_POINTS_FACTOR = 5l;

    private final AchievementRepository achievementRepository;

    private final AnswerAchievementRepository answerAchievementRepository;

    private final ApplicationEventPublisher publisher;

    private final BroadContext broadContext;

    @Autowired
    public AchievementService(AchievementRepository achievementRepository, AnswerAchievementRepository answerAchievementRepository, ApplicationEventPublisher publisher, BroadContext broadContext) {
        this.achievementRepository = achievementRepository;
        this.answerAchievementRepository = answerAchievementRepository;
        this.publisher = publisher;
        this.broadContext = broadContext;
    }

    public void calculateAchievements(Challenge challenge) {
        checkArgument(challenge!=null);
        checkArgument(challenge.getMet());

        ChallengeAchievement achievementChallenger = createPointAchievementChallenger(challenge);
        publisher.publishEvent(new ChallengeAchievementCreatedEvent(achievementChallenger, broadContext.get()));
        achievementRepository.save(achievementChallenger);

        ChallengeAchievement achievementChallenged = createPointAchievementChallenged(challenge);
        publisher.publishEvent(new ChallengeAchievementCreatedEvent(achievementChallenged,broadContext.get()));
        achievementRepository.save(achievementChallenged);

    }

    private ChallengeAchievement createPointAchievementChallenger(Challenge challenge) {
        ChallengeAchievement achievement = createPointAchievement(challenge);
        achievement.setUser(challenge.getChallenger());
        return achievement;
    }

    private ChallengeAchievement createPointAchievementChallenged(Challenge challenge) {
        ChallengeAchievement achievement = createPointAchievement(challenge);
        achievement.setUser(challenge.getChallenged());
        return achievement;
    }

    private ChallengeAchievement createPointAchievement(Challenge challenge) {
        ChallengeAchievement achievement = new ChallengeAchievement();
        achievement.setChallenge(challenge);
        achievement.setValue(calculatePoints(challenge));
        return achievement;
    }


    public Long calculatePoints(Challenge challenge) {
        checkArgument(challenge != null);

        Long basicPoints = 0l;
        if (challenge.getMet()){
            basicPoints = getPoints(challenge.getQuestion().getLevel());
        }
        return Math.floorDiv(basicPoints, CHALLENGE_POINTS_FACTOR);

    }

    public void calculateAchievements(Answer answer) {
        checkArgument(answer != null);

        AnswerAchievement achievement = createPointAchievement(answer);

        publisher.publishEvent(new AnswerAchievementCreatedEvent(achievement, broadContext.get()));

        achievementRepository.save(achievement);
    }

    private AnswerAchievement createPointAchievement(Answer answer) {
        AnswerAchievement achievement = new AnswerAchievement();
        achievement.setAnswer(answer);
        achievement.setValue(calculatePoints(answer));
        achievement.setUser(answer.getUser());
        return achievement;
    }

    private Long calculatePoints(Answer answer){
        Long points = 0l;
        if (answer.getCorrect()) {
            Level level = answer.getQuestion().getLevel();
            points = getPoints(level);
        }
        return points;
    }

    public Long getPoints(Level level) {
        Long points;
        points = LevelPoints.getPoints(level);
        return points;
    }

    public Iterable<Achievement> achievementsOf(User user) {
        return achievementRepository.findAllByUser(user);
    }

    public Optional<Achievement> findByAnswerId(Optional<String> answerId) {
        checkArgument(answerId != null);
        return new EntitySupplier<>(answerId, this::getAnswerAchievementById).supply();
    }

    private Optional<Achievement> getAnswerAchievementById(String id) {
        return Optional.ofNullable(answerAchievementRepository.findOne(id));
    }

}
