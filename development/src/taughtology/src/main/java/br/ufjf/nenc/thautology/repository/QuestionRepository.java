package br.ufjf.nenc.thautology.repository;

import br.ufjf.nenc.thautology.model.Level;
import br.ufjf.nenc.thautology.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends PagingAndSortingRepository<Question, String>{

    long countByLevel(Level level);

    Page<Question> findByIdNotInAndLevel(List<String> answeredQuestionIds, Level level, Pageable pageRequest);
}
