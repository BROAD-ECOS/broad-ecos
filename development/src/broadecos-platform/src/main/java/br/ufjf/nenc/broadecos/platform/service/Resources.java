package br.ufjf.nenc.broadecos.platform.service;

import br.ufjf.nenc.broadecos.platform.api.Api;
import br.ufjf.nenc.broadecos.platform.api.Method;
import br.ufjf.nenc.broadecos.platform.api.Resource;
import br.ufjf.nenc.broadecos.platform.model.MatchedResource;
import br.ufjf.nenc.broadecos.platform.util.Processor;
import com.google.common.collect.ImmutableSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Collections.emptyMap;

@Component
public class Resources {

    private final String API_RESOURCES_FILE = "/br/ufjf/nenc/broadecos/platform/api/broadecos-api.xml";

    private final Set<Resource> resources;

    @Autowired
    public Resources(Processor processor) {
         resources = ImmutableSet.copyOf(processor.unmarshall(resourceAsStream(), Api.class).getResources());
    }

    private InputStream resourceAsStream() {
        return getClass().getResourceAsStream(API_RESOURCES_FILE);
    }

    public Optional<MatchedResource> get(Method method, String path) {
        checkArgument(method != null);
        checkArgument(path != null);

        return resources.stream()
            .filter((r)->r.getMethod() == method)
            .map((r)-> r.match(path))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .findFirst();
    }
}
