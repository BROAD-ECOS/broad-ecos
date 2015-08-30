package br.ufjf.nenc.thautology.resolver;

import br.ufjf.nenc.broadecos.Context;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * Created by welingtonveiga on 29/08/15.
 */
public class AbstractContextArgumentResolver {
    private static final String TOKEN_HEADER = "broad-ecos-token";
    private static final String PLATFORM_HEADER = "broad-ecos-platform";

    protected Context buildContext(NativeWebRequest webRequest) {
        return Context.builder()
                .token(webRequest.getHeader(TOKEN_HEADER))
                .platform(webRequest.getHeader(PLATFORM_HEADER))
                .build();
    }
}
