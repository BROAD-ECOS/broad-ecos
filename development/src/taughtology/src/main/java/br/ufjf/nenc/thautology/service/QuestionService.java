package br.ufjf.nenc.thautology.service;

import br.ufjf.nenc.thautology.event.LevelCompleteEvent;
import br.ufjf.nenc.thautology.model.Answer;
import br.ufjf.nenc.thautology.model.Level;
import br.ufjf.nenc.thautology.model.Question;
import br.ufjf.nenc.thautology.model.User;
import br.ufjf.nenc.thautology.repository.QuestionRepository;
import br.ufjf.nenc.thautology.util.IterableList;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.*;
import static com.google.common.base.Preconditions.checkArgument;
import static java.util.stream.Collectors.toList;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final AnswerService answerService;

    private final ApplicationEventPublisher publisher;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, AnswerService answerService, ApplicationEventPublisher publisher) {
        this.questionRepository = questionRepository;
        this.answerService = answerService;
        this.publisher = publisher;
    }

    public Page<Question> findAll(PageRequest pageRequest) {
        return questionRepository.findAll(pageRequest);
    }

    public Optional<Question> nextQuestion(User user) {
        checkArgument(user != null);

        Optional<Question> randomQuestion = randomQuestionByUser(user);

        if (!randomQuestion.isPresent()) {
            publisher.publishEvent(LevelCompleteEvent.from(user));
        }

        return randomQuestion;
    }

    private Optional<Question> randomQuestionByUser(User user) {
        Level level = user.getLevel();

        Optional<Question> randomQuestion = Optional.empty();

        List<Answer> answers = new IterableList<>(answerService.answeredBy(user, level));

        List<String> answeredQuestionIds = answers.stream()
                .filter(a -> a.getQuestion().getLevel() == level)
                .map(a -> a.getQuestion().getId())
                .collect(toList());

        int random = randomQuestionIndex(answeredQuestionIds.size(), level);
        PageRequest page = new PageRequest(random, 1);

        Page<Question> questionPage = questionRepository.findByIdNotInAndLevel(answeredQuestionIds, level, page);

        if (!questionPage.getContent().isEmpty()) {
            randomQuestion = Optional.of(questionPage.getContent().get(0));
        }
        return randomQuestion;
    }

    private int randomQuestionIndex(int totallevelAnsweredQuestion, Level level) {
        long totalUnAnwseredQuestionsForLevel = questionRepository.countByLevel(level) - totallevelAnsweredQuestion;
        return (int) Math.floor(Math.random()*totalUnAnwseredQuestionsForLevel);
    }

    public Optional<Question> getQuestionById(String questionId) {
        checkArgument(questionId != null);

        return Optional.ofNullable(questionRepository.findOne(questionId));
    }
}
