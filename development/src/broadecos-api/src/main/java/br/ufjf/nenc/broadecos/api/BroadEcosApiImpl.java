package br.ufjf.nenc.broadecos.api;

import br.ufjf.nenc.broadecos.api.exception.BroadEcosApiException;
import br.ufjf.nenc.broadecos.api.experience.ExperienceRequest;
import br.ufjf.nenc.broadecos.api.experience.ExperienceResponse;
import br.ufjf.nenc.broadecos.api.experience.ExperienceStatement;
import br.ufjf.nenc.broadecos.api.model.Course;
import br.ufjf.nenc.broadecos.api.model.ParticipantProfile;
import br.ufjf.nenc.broadecos.api.model.PlatformInfo;
import br.ufjf.nenc.broadecos.api.model.Reference;
import br.ufjf.nenc.broadecos.api.util.ISO8601Date;
import com.google.common.base.Preconditions;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.UniformInterfaceException;
import javaslang.control.Match;
import javaslang.control.Try;
import lombok.Builder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static br.ufjf.nenc.broadecos.api.util.PreconditionPredicates.isNotNull;
import static com.google.common.base.Preconditions.*;
import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.*;
import static java.net.URLEncoder.*;

public class BroadEcosApiImpl implements BroadEcosApi {

    private static final int NOT_FOUND = 404;
    private final SecureWSClient wsClient;

    public BroadEcosApiImpl(Context context) {
        checkArgument(isNotNull(context));
        this.wsClient = new SecureWSClient(context);
    }

    @Override
    public ParticipantProfile getCurrentParticipant() {
        return tryOf(()-> wsClient.get("/me/profile", ParticipantProfile.class));
    }

    @Override
    public Optional<ParticipantProfile> getParticipant(String id) {
        checkArgument(isNotNull(id));
        return tryOf(()-> {
            Optional<ParticipantProfile> participant = Optional.empty();

            try {
                participant = Optional.of(wsClient.get("/participants/" + id, ParticipantProfile.class));
            } catch (UniformInterfaceException e) {
                if (e.getResponse().getStatus() != NOT_FOUND) {
                    throw e;
                }
            }

            return participant;
        });
    }

    @Override
    public PlatformInfo getPlatFormInfo() {
        return tryOf(()-> wsClient.get("/platform-info", PlatformInfo.class));
    }

    @Override
    public Course getCurrentCourse() {
        return  tryOf(() -> wsClient.get("/courses/current", Course.class));
    }

    @Override
    public List<ParticipantProfile> getCurrentCourseParticipants() {
        return tryOf(() -> {
            ClientResponse clientResponse = wsClient.getList("/courses/current/participants");
            return clientResponse.getEntity(new GenericType<List<ParticipantProfile>>() {
            });
        });
    }

    @Override
    public Reference sendExperience(ExperienceStatement statement) {
        return  tryOf(() -> wsClient.postCreation("/experiences", statement));
    }

    @Override
    public ExperienceResponse getExperience(ExperienceRequest experienceRequest) {
        return tryOf(()-> {
            String path = buildExperiencePath(experienceRequest);
            return wsClient.get(path, ExperienceResponse.class);
        });
    }

    private String buildExperiencePath(ExperienceRequest request) {
        checkArgument(isNotNull(request));

        return tryOf(()-> {
            String experiencePath = "/experiences";
            Optional<String> params = request.params().stream()
                    .map(this::buildParamString)
                    .reduce((a, b) -> a + '&' + b);

            if (params.isPresent()) {
                experiencePath += '?' + params.get();
            }

            return experiencePath;
        });
    }

    private String buildParamString(ExperienceRequest.Param p) {
        return tryOf(() -> format("%s=%s", p.getName(), encode(p.getValue(), "UTF-8")));
    }

    private <T> T tryOf(Try.CheckedSupplier<T> block) {
         try {
             return block.get();
         } catch (Throwable t) {
             throw new BroadEcosApiException(t);
         }
    }
}
