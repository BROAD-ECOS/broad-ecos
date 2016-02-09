package br.ufjf.nenc.eenrollment.service;

import br.ufjf.nenc.broadecos.api.Context;
import br.ufjf.nenc.eenrollment.model.EnrollData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class EnrollService {

    private final BroadService broadService;

    @Autowired
    public EnrollService(BroadService broadService) {
        this.broadService = broadService;
    }

    public EnrollData enroll(EnrollData enrollData, Context context) {
        return broadService.enroll(enrollData, context);
    }
}
