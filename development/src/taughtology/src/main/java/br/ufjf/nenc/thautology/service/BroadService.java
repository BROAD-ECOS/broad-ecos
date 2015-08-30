package br.ufjf.nenc.thautology.service;

import br.ufjf.nenc.broadecos.BroadEcosApi;
import br.ufjf.nenc.broadecos.Context;
import br.ufjf.nenc.broadecos.model.Course;
import br.ufjf.nenc.broadecos.model.PlatformInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BroadService {

    @Autowired
    private final BroadEcosApi broadEcosApi;

    @Autowired
    public BroadService(BroadEcosApi broadEcosApi) {
        this.broadEcosApi = broadEcosApi;
    }

    public PlatformInfo getPlatformInfo(Context context){
        return broadEcosApi.withContext(context).getPlatFormInfo();
    }

    public Course getCurrentCourse(Context context) {
        return broadEcosApi.withContext(context).getCurrentCourse();
    }
}
