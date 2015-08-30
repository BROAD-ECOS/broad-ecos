package br.ufjf.nenc.thautology.repository;

import br.ufjf.nenc.thautology.model.Level;
import br.ufjf.nenc.thautology.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends PagingAndSortingRepository<Question, String>{

    long countByLevel(Level level);

    Page<Question> findByLevel(Level level, Pageable pageRequest);
}
