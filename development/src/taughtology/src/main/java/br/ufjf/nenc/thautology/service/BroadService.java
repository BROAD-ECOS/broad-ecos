package br.ufjf.nenc.thautology.service;

import br.ufjf.nenc.broadecos.api.BroadEcosApi;
import br.ufjf.nenc.broadecos.api.BroadEcosApiProvider;
import br.ufjf.nenc.broadecos.api.Context;
import br.ufjf.nenc.broadecos.api.model.Course;
import br.ufjf.nenc.broadecos.api.model.PlatformInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BroadService {

    @Autowired
    private final BroadEcosApiProvider broadEcosApiProvider;

    @Autowired
    public BroadService(BroadEcosApiProvider broadEcosApiProvider) {
        this.broadEcosApiProvider = broadEcosApiProvider;
    }

    public PlatformInfo getPlatformInfo(Context context){
        System.out.println(context);
        return broadEcosApiProvider.withContext(context).getPlatFormInfo();
    }

    public Course getCurrentCourse(Context context) {
        return broadEcosApiProvider.withContext(context).getCurrentCourse();
    }
}
