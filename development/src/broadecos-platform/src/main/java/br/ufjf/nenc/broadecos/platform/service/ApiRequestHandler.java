package br.ufjf.nenc.broadecos.platform.service;

import br.ufjf.nenc.broadecos.platform.api.Resource;
import br.ufjf.nenc.broadecos.platform.api.ResourceType;
import br.ufjf.nenc.broadecos.platform.excp.PreconditionsFailedException;
import br.ufjf.nenc.broadecos.platform.excp.ResourceNotFoundException;
import br.ufjf.nenc.broadecos.platform.excp.UnauthorizedResourceException;
import br.ufjf.nenc.broadecos.platform.handler.ResourceHandler;
import br.ufjf.nenc.broadecos.platform.model.ApiRequest;
import br.ufjf.nenc.broadecos.platform.model.MatchedResource;
import br.ufjf.nenc.broadecos.platform.model.TokenContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ApiRequestHandler {

    private final Resources resources;

    private final List<ResourceHandler> handlers;


    @Autowired
    public ApiRequestHandler(Resources resources, List<ResourceHandler> handlers) {
        this.resources = resources;
        this.handlers = handlers;
    }

    public Object handle(ApiRequest request) {
        Optional<MatchedResource> resource = resources.get(request.getMethod(), request.getPath());
        return handleResource(request,  resource.orElseThrow(ResourceNotFoundException::new));
    }

    private Object handleResource(ApiRequest request, MatchedResource resource) {
        TokenContext tokenContext = request.getTokenContext();
        if (tokenContext.hasScopes(resource.getResource().getRequiredScope())) {
            if (request.met(resource)) {
                Optional<ResourceHandler> handler = typeHandler(resource.getResource().getType());
                if (handler.isPresent()) {
                    return handler.get().handle(resource, tokenContext, request.getParams());
                } else {
                    throw new PreconditionsFailedException("Unsuported Resource Type");
                }
            } else {
                throw new PreconditionsFailedException();
            }
        } else {
            throw new UnauthorizedResourceException();
        }
    }

    private Optional<ResourceHandler> typeHandler(ResourceType type) {
        return handlers.stream().filter((h)->h.handles(type)).findFirst();
    }


}
