package br.ufjf.nenc.broadecos.api;

import br.ufjf.nenc.broadecos.api.experience.ExperienceStatement;
import br.ufjf.nenc.broadecos.api.model.Course;
import br.ufjf.nenc.broadecos.api.model.ParticipantProfile;
import br.ufjf.nenc.broadecos.api.model.PlatformInfo;
import br.ufjf.nenc.broadecos.api.model.Reference;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.LoggingFilter;
import lombok.Builder;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

@Builder
public class BroadEcosApiContext {

    private final Context context;


    private Client client() {
        ClientConfig cfg = new DefaultClientConfig();
        cfg.getClasses().add(JacksonJsonProvider.class);
        Client  client = Client.create(cfg);
        client.addFilter(new LoggingFilter(System.out));
        return client;
    }

    private <T> T get(String path, Class<T> resourceType) {
        WebResource webResource = client().resource(context.getPlatform() + path);
        return webResource
                .type(APPLICATION_JSON_TYPE)
                .accept(APPLICATION_JSON_TYPE)
                .header("broad-ecos-token", context.getToken())
                .get(resourceType);
    }

    private ClientResponse getList(String path) {
        WebResource webResource = client().resource(context.getPlatform()+path);
        return webResource
                .type(APPLICATION_JSON_TYPE)
                .accept(APPLICATION_JSON_TYPE)
                .header("broad-ecos-token", context.getToken())
                .get(ClientResponse.class);

    }

    @SuppressWarnings(value = "unchecked")
    private <T> T post(String path, T data) {
        WebResource webResource = client().resource(context.getPlatform() + path);
        return webResource
                .type(APPLICATION_JSON_TYPE)
                .accept(APPLICATION_JSON_TYPE)
                .header("broad-ecos-token", context.getToken())
                .post((Class<T>) data.getClass(), data);
    }

    @SuppressWarnings(value = "unchecked")
    private Reference postCreation(String path, Object data) {
        WebResource webResource = client().resource(context.getPlatform() + path);
        return webResource
                .type(APPLICATION_JSON_TYPE)
                .accept(APPLICATION_JSON_TYPE)
                .header("broad-ecos-token", context.getToken())
                .post(Reference.class, data);
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
        ClientResponse clientResponse = getList("/courses/current/participants");
        return clientResponse.getEntity(new GenericType<List<ParticipantProfile>>() {});
    }

    public Reference sendExperience(ExperienceStatement statement) {
        return postCreation("/experiences", statement);
    }
}
