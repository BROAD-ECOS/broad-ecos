package br.ufjf.nenc.broadecos.platform.model;

import br.ufjf.nenc.broadecos.platform.api.Method;
import br.ufjf.nenc.broadecos.platform.api.Param;
import br.ufjf.nenc.broadecos.platform.api.Resource;
import lombok.Builder;
import lombok.Getter;

import static com.google.common.base.Preconditions.checkArgument;

@Builder
@Getter
public class ApiRequest {

    private final Method method;

    private final String path;

    private final TokenContext tokenContext;

    private final RequestParams params;

    public boolean met(MatchedResource resource) {
        checkArgument(resource != null);

        return resource.getResource().getParams().stream()
                .filter(Param::getRequired)
                .noneMatch((p) -> !isQueryParam(p) && !resource.hasUrlParam(p));
    }

    private boolean isQueryParam(Param p) {
        return params.getParams().containsKey(p.getName());
    }
}
