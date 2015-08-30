package br.ufjf.nenc.thautology.service;

import br.ufjf.nenc.thautology.model.Question;
import br.ufjf.nenc.thautology.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Page<Question> findAll(PageRequest pageRequest) {
        return questionRepository.findAll(pageRequest);
    }

}
