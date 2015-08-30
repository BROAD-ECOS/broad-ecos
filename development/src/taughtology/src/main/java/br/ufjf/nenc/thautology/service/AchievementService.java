package br.ufjf.nenc.thautology.service;

import br.ufjf.nenc.thautology.event.AchievementCreatedEvent;
import br.ufjf.nenc.thautology.model.*;
import br.ufjf.nenc.thautology.repository.AchievementRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
public class AchievementService {

    private final AchievementRepository achievementRepository;

    private final ApplicationEventPublisher publisher;

    @Autowired
    public AchievementService(AchievementRepository achievementRepository, ApplicationEventPublisher publisher) {
        this.achievementRepository = achievementRepository;
        this.publisher = publisher;
    }

    public void calculateAchievements(Answer answer) {
        checkArgument(answer != null);

        Achievement achievement = createPointAchievement(answer);

        publisher.publishEvent(new AchievementCreatedEvent(achievement));

        achievementRepository.save(achievement);
    }

    private Achievement createPointAchievement(Answer answer) {
        Achievement achievement = new Achievement();
        achievement.setRelatedAnswer(answer);
        achievement.setValue(calculatePoints(answer));
        return achievement;
    }

    private Long calculatePoints(Answer answer){
        Long points = 0l;
        if (answer.getCorrect()) {
            Level level = answer.getQuestion().getLevel();
            points = LevelPoints.getPoints(level);
        }
        return points;
    }

    public Optional<Achievement> findByAnswerId(Optional<String> answerId) {
        return achievementRepository.findOneByRelatedAnswerId(answerId.get());
    }

    public Iterable<Achievement> achievementsBy(User user) {
        checkArgument(user != null && user.getId()!=null);
        return achievementRepository.findOneByRelatedAnswerUser(user);
    }

    public Iterable<Achievement> achievementsOf(List<Answer> answers) {
        checkArgument(answers != null);
        Set<String> answerIds = answers.stream().map(Answer::getId).collect(toSet());

        return achievementRepository.findAllByRelatedAnswerIdIn(answerIds);
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
