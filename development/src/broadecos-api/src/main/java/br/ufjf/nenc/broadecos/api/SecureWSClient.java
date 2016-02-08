package br.ufjf.nenc.broadecos.api;

import br.ufjf.nenc.broadecos.api.model.Reference;
import com.google.common.collect.ImmutableMap;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.LoggingFilter;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import javax.ws.rs.core.MediaType;
import java.util.Map;

class SecureWSClient extends WSClient {

    private final Context context;

    private final Map<String, String> headers;

    SecureWSClient(Context context) {
        this.context = context;
        this.headers = ImmutableMap. <String, String> builder()
                .put("broad-ecos-token", context.getToken())
                .build();

    }

    <T> T get(String path, Class<T> resourceType) {
        return get(context.getPlatform(), path, resourceType, headers);
    }

    ClientResponse getList(String path) {
        return getList(context.getPlatform(), path, headers);

    }

    @SuppressWarnings(value = "unchecked")
    <T> T post(String path, T data) {
        return post(context.getPlatform(), path, data, headers);
    }

    @SuppressWarnings(value = "unchecked")
    Reference postCreation(String path, Object data) {
        return postCreation(context.getPlatform(), path, data, headers);
    }

}