package br.ufjf.nenc.broadecos.api;

import br.ufjf.nenc.broadecos.api.experience.ExperienceStatement;
import br.ufjf.nenc.broadecos.api.model.Course;
import br.ufjf.nenc.broadecos.api.model.ParticipantProfile;
import br.ufjf.nenc.broadecos.api.model.PlatformInfo;
import br.ufjf.nenc.broadecos.api.model.Reference;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import lombok.Builder;

import java.util.List;

public class BroadEcosApiImpl implements BroadEcosApi {

    private final SecureWSClient wsClient;

    public BroadEcosApiImpl(Context context) {
        this.wsClient = new SecureWSClient(context);
    }
    @Override
    public ParticipantProfile getParticipant() {
        return wsClient.get("/me/profile", ParticipantProfile.class);
    }

    @Override
    public PlatformInfo getPlatFormInfo() {
        return wsClient.get("/platform-info", PlatformInfo.class);
    }

    @Override
    public Course getCurrentCourse() {
        return wsClient.get("/courses/current", Course.class);
    }

    @Override
    public List<ParticipantProfile> getCurrentCourseParticipants() {
        ClientResponse clientResponse = wsClient.getList("/courses/current/participants");
        return clientResponse.getEntity(new GenericType<List<ParticipantProfile>>() {});
    }

    @Override
    public Reference sendExperience(ExperienceStatement statement) {
        return wsClient.postCreation("/experiences", statement);
    }
}
