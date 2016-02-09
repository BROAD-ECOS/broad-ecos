package br.ufjf.nenc.eenrollment.service;

import br.ufjf.nenc.broadecos.api.BroadEcosApiProvider;
import br.ufjf.nenc.broadecos.api.Context;
import br.ufjf.nenc.broadecos.api.model.AvaliableExtensions;
import br.ufjf.nenc.broadecos.api.model.Course;
import br.ufjf.nenc.broadecos.api.model.ParticipantProfile;
import br.ufjf.nenc.broadecos.api.model.PlatformInfo;
import br.ufjf.nenc.eenrollment.model.EnrollData;
import java.util.Optional;
import com.google.common.collect.ImmutableSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class BroadService {

    public static final String EXTENSION_ID = "http://www.example.org/x-enroll";

    @Autowired
    private final BroadEcosApiProvider broadEcosApiProvider;

    @Autowired
    public BroadService(BroadEcosApiProvider broadEcosApiProvider) {
        this.broadEcosApiProvider = broadEcosApiProvider;
    }

    public PlatformInfo getPlatformInfo(Context context){
        return broadEcosApiProvider.withContext(context).getPlatFormInfo();
    }

    public Course getCurrentCourse(Context context) {
        return broadEcosApiProvider.withContext(context).getCurrentCourse();
    }

    public Set<ParticipantProfile> getCurrentCourseParticipants(Context context){
        return ImmutableSet.copyOf(broadEcosApiProvider.withContext(context).getCurrentCourseParticipants());
    }

    public Set<ParticipantProfile> getAllPlatformParticipants(Context context){
        return ImmutableSet.copyOf(broadEcosApiProvider.withContext(context).getAllParticipants());
    }

    public EnrollData enroll(EnrollData enrollData, Context context) {

        PlatformInfo platformInfo = getPlatformInfo(context);
        Optional<AvaliableExtensions> extension = platformInfo.getExtensions()
                .stream()
                .filter((x) -> x.getId().equals(EXTENSION_ID))
                .findFirst();
        System.out.println(enrollData);
        if (extension.isPresent()) {
            return  broadEcosApiProvider.withContext(context).post("/"+extension.get().getNamespace()+"/enroll", enrollData);
        } else {
            throw new IllegalStateException("Extension "+EXTENSION_ID+" Not found.");
        }
    }
}
