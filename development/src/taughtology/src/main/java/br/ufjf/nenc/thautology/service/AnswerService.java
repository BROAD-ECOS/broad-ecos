package br.ufjf.nenc.thautology.service;

import br.ufjf.nenc.thautology.component.BroadContext;
import br.ufjf.nenc.thautology.event.AnswerCreatedEvent;
import br.ufjf.nenc.thautology.event.EntityCreatedEvent;
import br.ufjf.nenc.thautology.event.EntityUpdatedEvent;
import br.ufjf.nenc.thautology.exception.QuestionAlreadyAnsweredException;
import br.ufjf.nenc.thautology.model.Answer;
import br.ufjf.nenc.thautology.model.Level;
import br.ufjf.nenc.thautology.model.User;
import br.ufjf.nenc.thautology.repository.AnswerRepository;
import com.google.common.base.Preconditions;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.stream.Collectors.toList;

@Service
@Log4j
public class AnswerService {

    private final ApplicationEventPublisher publisher;

    private final AnswerRepository answerRepository;

    private final BroadContext broadContext;

    @Autowired
    public AnswerService(ApplicationEventPublisher publisher, AnswerRepository answerRepository, BroadContext broadContext) {
        this.publisher = publisher;
        this.answerRepository = answerRepository;
        this.broadContext = broadContext;
    }

    public Answer save(Answer answer) {

        String answerId = answer.getId();

        final Answer savedAnswer;
        if (answerId!=null) {
            savedAnswer = update(answer);
        } else {
            savedAnswer = create(answer);
        }

        return  savedAnswer;
    }

    private Answer update(Answer answer) {
        Answer savedAnswer = answerRepository.save(answer);
        publisher.publishEvent(EntityUpdatedEvent.from(savedAnswer, broadContext.get()));

        log.info("Answer updated: " + answer);
        return savedAnswer;
    }

    private Answer create(Answer answer) {
        Answer savedAnswer;
        if (!userAnsweredQuestion(answer)) {
            savedAnswer = answerRepository.save(answer);
            publisher.publishEvent(new AnswerCreatedEvent(answer, broadContext.get()));
        } else {
            log.error("Question already answered by user, trying to save: " + answer);
            throw new QuestionAlreadyAnsweredException();
        }
        log.info("Answer created: " + answer);

        return savedAnswer;
    }

    public boolean userAnsweredQuestion(Answer answer) {
        return getUserAnswersByQuestion(answer).iterator().hasNext();
    }

    public Iterable<Answer> getUserAnswersByQuestion(Answer answer) {
        String userId = answer.getUser().getId();
        String questionId = answer.getQuestion().getId();
        return answerRepository.findByUserIdAndQuestionId(userId, questionId);
    }

    public Iterable<Answer> answeredBy(User user, Level level){
        checkArgument(user != null && level != null);
        return allByUserId(user.getId());
    }

    public Iterable<Answer> answeredBy(User user){
        return answerRepository.findAllByUserId(user.getId());
    }


    public Answer loadById(String id) {
        return answerRepository.findOne(id);
    }

    public Iterable<Answer> allByUserId(String userId) {
        checkArgument(userId != null);
        return answerRepository.findAllByUserId(userId);
    }
}
