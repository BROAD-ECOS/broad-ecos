package br.ufjf.nenc.thautology.rest;


import br.ufjf.nenc.thautology.model.Comment;
import br.ufjf.nenc.thautology.model.Reference;
import br.ufjf.nenc.thautology.service.CommentService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/comments")
@Log4j
public class CommentResource {

    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int DEFAULT_PAGE_NUMBER = 1;
    private final CommentService commentService;

    @Autowired
    public CommentResource(CommentService commentService) {
        this.commentService = commentService;
    }

    @RequestMapping(method = GET)
    public Page<Comment> get(@RequestParam("answerId") Optional<String> answerId,
                             @RequestParam("page") Optional<Integer> page,
                             @RequestParam("pageSize") Optional<Integer> pageSize){

        PageRequest pageRequest = new PageRequest(page.orElse(DEFAULT_PAGE_NUMBER),pageSize.orElse(DEFAULT_PAGE_SIZE));

        return  commentService.findByQuestionId(answerId.get(), pageRequest);
    }

    @RequestMapping(method = POST)
    public Reference<Comment> post(@RequestBody @Valid Comment comment){

        log.info("Comment received: " + comment);

        Comment savedComment = commentService.save(comment);

        return Reference.to(savedComment);

    }

}
