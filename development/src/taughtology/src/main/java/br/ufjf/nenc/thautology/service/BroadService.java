package br.ufjf.nenc.thautology.service;

import br.ufjf.nenc.broadecos.api.BroadEcosApi;
import br.ufjf.nenc.broadecos.api.Context;
import br.ufjf.nenc.broadecos.api.model.Course;
import br.ufjf.nenc.broadecos.api.model.PlatformInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
