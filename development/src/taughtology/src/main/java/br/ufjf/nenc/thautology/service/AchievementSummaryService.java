package br.ufjf.nenc.thautology.service;

import br.ufjf.nenc.thautology.model.Achievement;
import br.ufjf.nenc.thautology.model.AchievementSummary;
import br.ufjf.nenc.thautology.model.Answer;
import br.ufjf.nenc.thautology.model.User;
import br.ufjf.nenc.thautology.util.IterableList;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j
public class AchievementSummaryService {

    private final AchievementService achievementService;

    private final UserService userService;

    private final AnswerService answerService;

    @Autowired
    public AchievementSummaryService(AchievementService achievementService, UserService userService, AnswerService answerService) {
        this.achievementService = achievementService;
        this.userService = userService;
        this.answerService = answerService;
    }


    public AchievementSummary getUseAchievementrSummary(String userId) {

        AchievementSummary summary;

        Optional<User> userOptional = userService.getUser(userId);
        if (userOptional.isPresent()) {
            summary = buildSummaryByUser(userOptional.get());
        } else {
           throw new IllegalArgumentException("Unknown user id: "+userId);
        }

        return summary;
    }

    private AchievementSummary buildSummaryByUser(User user) {

        List<Answer> answers = new IterableList<>(answerService.answeredBy(user));
        List<Achievement> achievements = new IterableList<>(achievementService.achievementsOf(user));

        AchievementSummary summary = new AchievementSummary();

        summary.setQuestionsAnswered((long) answers.size());

        summary.setCorrectAnswers(answers.stream().filter(Answer::getCorrect).count());

        summary.setTotalPoints(achievements.stream().mapToLong(Achievement::getValue).reduce(0l, (t, v) -> t + v));

        return summary;
    }
}
