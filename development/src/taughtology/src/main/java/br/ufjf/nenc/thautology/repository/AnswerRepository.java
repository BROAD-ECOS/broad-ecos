package br.ufjf.nenc.thautology.repository;

import br.ufjf.nenc.thautology.model.Answer;
import br.ufjf.nenc.thautology.model.Level;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends PagingAndSortingRepository<Answer, String> {

    Iterable<Answer> findByUserIdAndQuestionId(String userId, String questionId);

    Iterable<Answer> findAllByUserId(String userId);
}
