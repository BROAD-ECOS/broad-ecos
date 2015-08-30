package br.ufjf.nenc.thautology.service;

import br.ufjf.nenc.thautology.model.Comment;
import br.ufjf.nenc.thautology.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment save(Comment comment) {
        comment.setCreated(new Date());
        comment.setLastUpdated(new Date());

        return commentRepository.save(comment);
    }

    public Page<Comment> findByQuestionId(String questionId, PageRequest pageRequest) {
        return commentRepository.findAllByQuestionIdOrderByCreatedDesc(questionId, pageRequest);
    }
}
