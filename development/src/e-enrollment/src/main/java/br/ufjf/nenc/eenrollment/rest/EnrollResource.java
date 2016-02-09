package br.ufjf.nenc.eenrollment.rest;

import br.ufjf.nenc.broadecos.api.Context;
import br.ufjf.nenc.eenrollment.model.EnrollData;
import br.ufjf.nenc.eenrollment.service.EnrollService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/enroll")
@Log4j
public class EnrollResource {

    private final EnrollService enrollService;

    @Autowired
    public EnrollResource(EnrollService enrollService) {
        this.enrollService = enrollService;
    }

    @RequestMapping(method = POST, consumes = "application/json")
    public EnrollData enroll(@RequestBody EnrollData enrollData, Context context) {
        log.info("Processing "+enrollData);
        return enrollService.enroll(enrollData, context);
    }

}
