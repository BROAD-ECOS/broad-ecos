package br.ufjf.nenc.thautology.rest;

import br.ufjf.nenc.broadecos.Context;
import br.ufjf.nenc.broadecos.model.Course;
import br.ufjf.nenc.broadecos.model.PlatformInfo;
import br.ufjf.nenc.thautology.model.Answer;
import br.ufjf.nenc.thautology.service.BroadService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/broad")
@Log4j
public class BroadInfoResource {

    private final BroadService broadService;

    @Autowired
    public BroadInfoResource(BroadService broadService) {
        this.broadService = broadService;
    }

    @RequestMapping(path = "/platform-info", method = GET)
    public PlatformInfo platformInfo(Context context){
        return broadService.getPlatformInfo(context);
    }


    @RequestMapping(path = "/course-info", method = GET)
    public Course courseInfo(Context context){
        return broadService.getCurrentCourse(context);
    }

}