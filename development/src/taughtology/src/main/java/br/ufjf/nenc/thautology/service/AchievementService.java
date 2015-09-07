package br.ufjf.nenc.thautology.service;

import br.ufjf.nenc.thautology.event.AchievementCreatedEvent;
import br.ufjf.nenc.thautology.model.*;
import br.ufjf.nenc.thautology.repository.AchievementRepository;
import br.ufjf.nenc.thautology.repository.AnswerAchievementRepository;
import br.ufjf.nenc.thautology.util.EntitySupplier;
import com.google.common.base.Preconditions;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.*;
import static com.google.common.base.Preconditions.checkArgument;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
public class AchievementService {

    private final Long CHALLENGE_POINTS_FACTOR = 5l;

    private final AchievementRepository achievementRepository;

    private final AnswerAchievementRepository answerAchievementRepository;

    private final ApplicationEventPublisher publisher;

    @Autowired
    public AchievementService(AchievementRepository achievementRepository, AnswerAchievementRepository answerAchievementRepository, ApplicationEventPublisher publisher) {
        this.achievementRepository = achievementRepository;
        this.answerAchievementRepository = answerAchievementRepository;
        this.publisher = publisher;
    }

    public void calculateAchievements(Challenge challenge) {
        checkArgument(challenge!=null);
        checkArgument(challenge.getMet());

        Achievement achievementChallenger = createPointAchievementChallenger(challenge);
        publisher.publishEvent(new AchievementCreatedEvent(achievementChallenger));
        achievementRepository.save(achievementChallenger);

        Achievement achievementChallenged = createPointAchievementChallenged(challenge);
        publisher.publishEvent(new AchievementCreatedEvent(achievementChallenged));
        achievementRepository.save(achievementChallenged);


    }

    private Achievement createPointAchievementChallenger(Challenge challenge) {
        Achievement achievement = createPointAchievement(challenge);
        achievement.setUser(challenge.getChallenger());
        return achievement;
    }

    private Achievement createPointAchievementChallenged(Challenge challenge) {
        Achievement achievement = createPointAchievement(challenge);
        achievement.setUser(challenge.getChallenged());
        return achievement;
    }

    private Achievement createPointAchievement(Challenge challenge) {
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

        Achievement achievement = createPointAchievement(answer);

        publisher.publishEvent(new AchievementCreatedEvent(achievement));

        achievementRepository.save(achievement);
    }

    private Achievement createPointAchievement(Answer answer) {
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

    private enum LevelPoints {
        EASY(Level.EASY, 5l),
        MEDIUM(Level.MEDIUM, 10l),
        HARD(Level.HARD, 25l),
        INSANE(Level.INSANE, 100l);

        @Getter private final Long points;
        @Getter private final Level level;

        LevelPoints(Level level, Long points) {
            this.level = level;
            this.points = points;
        }

        static Long getPoints(Level level) {
            for(LevelPoints levelPoints : values()) {
                if (level == levelPoints.level);
                return levelPoints.points;
            }
            throw new IllegalStateException("Unknow points for level "+level);
        }
    }
}
