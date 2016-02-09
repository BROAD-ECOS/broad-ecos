package br.ufjf.nenc.eenrollment.resolver;

import br.ufjf.nenc.broadecos.api.Context;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

public abstract class AbstractContextArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String TOKEN_HEADER = "broad-ecos-token";
    private static final String PLATFORM_HEADER = "broad-ecos-platform";

    protected Context buildContext(NativeWebRequest webRequest) {
        return Context.builder()
                .token(webRequest.getHeader(TOKEN_HEADER))
                .platform(webRequest.getHeader(PLATFORM_HEADER))
                .build();
    }
}
