package br.ufjf.nenc.broadecos.api;

import br.ufjf.nenc.broadecos.api.experience.ExperienceRequest;
import br.ufjf.nenc.broadecos.api.experience.ExperienceResponse;
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
        return clientResponse.getEntity(new GenericType<List<ParticipantProfile>>() {
        });
    }

    @Override
    public Reference sendExperience(ExperienceStatement statement) {
        return wsClient.postCreation("/experiences", statement);
    }

    @Override
    public ExperienceResponse getExperience(ExperienceRequest experienceRequest) {

        String path = buildExperiencePath(experienceRequest);
        return wsClient.get(path, ExperienceResponse.class);
    }

    private String buildExperiencePath(ExperienceRequest experienceRequest) {

        String path = "";

        if (experienceRequest.getVerbId() != null) {
            final String verbParamFormat = "verb=%s";
            path += String.format(verbParamFormat, experienceRequest.getVerbId().toString());
        }

        if (path.length() > 0) {
            path = "?"+path;
        }


        return "/experiences"+path;
    }
}
