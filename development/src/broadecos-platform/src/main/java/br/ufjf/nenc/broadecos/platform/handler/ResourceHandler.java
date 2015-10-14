package br.ufjf.nenc.broadecos.platform.handler;

import br.ufjf.nenc.broadecos.platform.api.Resource;
import br.ufjf.nenc.broadecos.platform.api.ResourceType;
import br.ufjf.nenc.broadecos.platform.model.MatchedResource;
import br.ufjf.nenc.broadecos.platform.model.RequestParams;
import br.ufjf.nenc.broadecos.platform.model.TokenContext;

public interface ResourceHandler {

    Boolean handles(ResourceType type);

    Object handle(MatchedResource resource, TokenContext context, RequestParams params);

}
