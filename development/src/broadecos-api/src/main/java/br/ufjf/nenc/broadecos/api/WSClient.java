package br.ufjf.nenc.broadecos.api;

import br.ufjf.nenc.broadecos.api.model.Reference;
import com.google.common.base.Supplier;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.LoggingFilter;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import javax.ws.rs.core.MediaType;

import java.util.Map;

import static com.google.common.base.Suppliers.memoize;

public class WSClient {

    private final Supplier<Client> client =  memoize(() -> {
        ClientConfig cfg = new DefaultClientConfig();
        cfg.getClasses().add(JacksonJsonProvider.class);
        Client client = Client.create(cfg);
        client.addFilter(new LoggingFilter(System.out));
        return client;
    });

    protected Client client() {
        return client.get();
    }

    protected <T> T get(String platform, String path, Class<T> resourceType, Map<String, String> additionalHeaders) {
        WebResource webResource = client().resource(platform + path);
        WebResource.Builder builder = webResource.type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE);

        additionalHeaders.entrySet()
                .stream()
                .forEach((e) -> builder.header(e.getKey(), e.getValue()));

        return builder.get(resourceType);
    }

    protected ClientResponse getList(String platform, String path, Map<String, String> additionalHeaders) {
        WebResource webResource = client().resource(platform + path);
        WebResource.Builder builder = webResource.type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE);

        additionalHeaders.entrySet()
                .stream()
                .forEach((e) -> builder.header(e.getKey(), e.getValue()));

        return builder.get(ClientResponse.class);

    }

    @SuppressWarnings(value = "unchecked")
    protected <T> T post(String platform, String path, T data, Map<String, String> additionalHeaders) {
        WebResource webResource = client().resource(platform + path);
        WebResource.Builder builder = webResource.type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE);

        additionalHeaders.entrySet()
                .stream()
                .forEach((e) -> builder.header(e.getKey(), e.getValue()));

        return builder.post((Class<T>) data.getClass(), data);
    }

    @SuppressWarnings(value = "unchecked")
    protected Reference postCreation(String platform, String path, Object data, Map<String, String> additionalHeaders) {
        WebResource webResource = client().resource(platform + path);
        WebResource.Builder builder = webResource.type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE);

        additionalHeaders.entrySet()
                .stream()
                .forEach((e) -> builder.header(e.getKey(), e.getValue()));

        return builder.post(Reference.class, data);
    }
}
