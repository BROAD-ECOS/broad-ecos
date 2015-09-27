package br.ufjf.nenc.broadecos.api;

import br.ufjf.nenc.broadecos.api.experience.ExperienceRequest;
import br.ufjf.nenc.broadecos.api.experience.ExperienceResponse;
import br.ufjf.nenc.broadecos.api.experience.ExperienceStatement;
import br.ufjf.nenc.broadecos.api.model.Course;
import br.ufjf.nenc.broadecos.api.model.ParticipantProfile;
import br.ufjf.nenc.broadecos.api.model.PlatformInfo;
import br.ufjf.nenc.broadecos.api.model.Reference;
import com.google.common.base.Preconditions;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.UniformInterfaceException;
import lombok.Builder;

import java.util.List;
import java.util.Optional;

import static br.ufjf.nenc.broadecos.api.util.PreconditionPredicates.isNotNull;
import static com.google.common.base.Preconditions.checkArgument;

public class BroadEcosApiImpl implements BroadEcosApi {

    private static final int NOT_FOUND = 404;
    private final SecureWSClient wsClient;

    public BroadEcosApiImpl(Context context) {
        this.wsClient = new SecureWSClient(context);
    }

    @Override
    public ParticipantProfile getCurrentParticipant() {
        return wsClient.get("/me/profile", ParticipantProfile.class);
    }

    @Override
    public Optional<ParticipantProfile> getParticipant(String id) {
        checkArgument(isNotNull(id));

        Optional participant = Optional.empty();

        try {
            participant = Optional.of(wsClient.get("/participants/"+id, ParticipantProfile.class));
        } catch (UniformInterfaceException e) {
            if (e.getResponse().getStatus() != NOT_FOUND) {
                throw e;
            }
        }

        return participant;
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
