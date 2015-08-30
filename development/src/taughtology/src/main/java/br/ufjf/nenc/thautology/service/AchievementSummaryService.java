package br.ufjf.nenc.thautology.service;

import br.ufjf.nenc.thautology.event.AchievementCreatedEvent;
import br.ufjf.nenc.thautology.model.*;
import br.ufjf.nenc.thautology.repository.AchievementRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

@Service
public class AchievementSummaryService {

    @Autowired
    public AchievementSummaryService(AchievementRepository achievementRepository, ApplicationEventPublisher publisher) {
        this.achievementRepository = achievementRepository;
        this.publisher = publisher;
    }

    private final AchievementRepository achievementRepository;

    private final ApplicationEventPublisher publisher;


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
