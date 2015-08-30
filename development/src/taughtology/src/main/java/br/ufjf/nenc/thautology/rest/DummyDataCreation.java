package br.ufjf.nenc.thautology.rest;

import br.ufjf.nenc.thautology.model.Entity;
import br.ufjf.nenc.thautology.model.Level;
import br.ufjf.nenc.thautology.model.Option;
import br.ufjf.nenc.thautology.model.Question;
import br.ufjf.nenc.thautology.repository.QuestionRepository;
import br.ufjf.nenc.thautology.repository.UserRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/dummy")
@Log4j
public class DummyDataCreation {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;


    @RequestMapping(method = GET)
    public List<Entity> create() {
        List<Entity> entities = new ArrayList<>();
        questionRepository.deleteAll();

        entities.add(questionRepository.save(Question.builder()
                .title("Ocorrência")
                .content("Sabe-se que a ocorrência de B é condição necessária para a ocorrência de C " +
                        "e condição suficiente para a ocorrência de D. " +
                        "Sabe-se, também, que a ocorrência de D é condição necessária e suficiente para a ocorrência de A. " +
                        "Assim, quando C ocorre, D não ocorre ou A não.")
                .level(Level.EASY)
                .solution(Boolean.FALSE)
                .build()));

        entities.add(questionRepository.save(Question.builder()
                .title("Nacionalidade")
                .content("Se Frederico é francês, então Alberto não é alemão. Ou Alberto é alemão, ou Egídio é espanhol." +
                        " Se Pedro não é português, então Frederico é francês. " +
                        "Ora, nem Egídio é espanhol nem Isaura é italiana. Logo Pedro é português e Alberto é alemão")
                .level(Level.EASY)
                .solution(Boolean.TRUE)
                .build()));


        entities.add(questionRepository.save(Question.builder()
                .title("Das cores")
                .content("Maria tem três carros: um Gol, um Corsa e um Fiesta. Um dos carros é branco, o outro é preto, " +
                        "e o outro é azul. Sabe-se que: 1) ou o Gol é branco, ou o Fiesta é branco, " +
                        "2) ou o Gol é preto, ou o Corsa é azul, 3) ou o Fiesta é azul, ou o Corsa é azul, " +
                        "4) ou o Corsa é preto, ou o Fiesta é preto. Portanto, as cores do Gol, do Corsa e do Fiesta são" +
                        " preto, branco, azul.")
                .level(Level.EASY)
                .solution(Boolean.FALSE)
                .build()));

        entities.add(questionRepository.save(Question.builder()
                .title("Páginas")
                .content(" Numa biblioteca há 2500 livros. Nenhum tem mais de 500 páginas. Pode-se afirmar que há pelo menos 3 livros com o mesmo número de páginas")
                .level(Level.EASY)
                .solution(Boolean.TRUE)
                .build()));

        return  entities;
    }


}
