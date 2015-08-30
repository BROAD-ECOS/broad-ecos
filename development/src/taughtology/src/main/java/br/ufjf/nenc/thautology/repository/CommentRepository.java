package br.ufjf.nenc.thautology.repository;

import br.ufjf.nenc.thautology.model.Achievement;
import br.ufjf.nenc.thautology.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment, String> {

    Page<Comment> findAllByQuestionIdOrderByCreatedDesc(String integer, Pageable pageable);
}
