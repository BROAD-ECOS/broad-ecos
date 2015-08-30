package br.ufjf.nenc.thautology.repository;

import br.ufjf.nenc.thautology.model.Achievement;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<Achievement, String> {

    Optional<Achievement> findOneByRelatedAnswerId(String answerId);
}
