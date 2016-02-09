package br.ufjf.nenc.eenrollment.rest;

import br.ufjf.nenc.broadecos.api.Context;
import br.ufjf.nenc.broadecos.api.model.Course;
import br.ufjf.nenc.broadecos.api.model.PlatformInfo;
import br.ufjf.nenc.eenrollment.service.BroadService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
