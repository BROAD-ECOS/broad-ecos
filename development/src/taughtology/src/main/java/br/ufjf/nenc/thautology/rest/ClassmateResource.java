package br.ufjf.nenc.thautology.rest;

import br.ufjf.nenc.broadecos.api.model.Course;
import br.ufjf.nenc.thautology.model.Classmate;
import br.ufjf.nenc.thautology.model.CurrentUser;
import br.ufjf.nenc.thautology.service.ClassmateService;
import br.ufjf.nenc.thautology.util.IterableList;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(path = "/classmates")
@Log4j
public class ClassmateResource {

    private final ClassmateService classmateService;

    @Autowired
    public ClassmateResource(ClassmateService classmateService) {
        this.classmateService = classmateService;
    }

    @RequestMapping(method = GET)
    public List<Classmate> getClassmates(CurrentUser currentUser, Course course){
        return new IterableList<>(classmateService.getClassmates(currentUser, course));
    }


}
