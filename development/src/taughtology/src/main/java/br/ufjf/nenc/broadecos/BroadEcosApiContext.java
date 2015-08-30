package br.ufjf.nenc.broadecos;

import br.ufjf.nenc.broadecos.model.Course;
import br.ufjf.nenc.broadecos.model.ParticipantProfile;
import br.ufjf.nenc.broadecos.model.PlatformInfo;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.LoggingFilter;
import lombok.Builder;

import javax.ws.rs.core.MediaType;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

@Builder
public class BroadEcosApiContext {

    private final Context context;

    private final Client client = Client.create();

    private <T> T get(String path, Class<T> resourceType) {
        WebResource webResource = client.resource(context.getPlatform()+path);
        client.addFilter(new LoggingFilter(System.out));
        return webResource
                .type(APPLICATION_JSON_TYPE)
                .accept(APPLICATION_JSON_TYPE)
                .header("broad-ecos-token", context.getToken())
                .get(resourceType);
    }

    private <T> List<T> getList(String path, Class<T> resourceType) {
        WebResource webResource = client.resource(context.getPlatform()+path);
        client.addFilter(new LoggingFilter(System.out));
        return webResource
                .type(APPLICATION_JSON_TYPE)
                .accept(APPLICATION_JSON_TYPE)
                .header("broad-ecos-token", context.getToken())
                .get(new GenericType<List<T>>(){});
    }

    public ParticipantProfile getParticipant() {
        return get("/me/profile", ParticipantProfile.class);
    }

    public PlatformInfo getPlatFormInfo() {
        return get("/platform-info", PlatformInfo.class);
    }

    public Course getCurrentCourse() {
        return get("/courses/current", Course.class);
    }


    public List<ParticipantProfile> getCurrentCourseParticipants() {
        return getList("/courses/current/participants", ParticipantProfile.class);
    }
}
