package br.ufjf.nenc.broadecos.rankr.resolver;

import br.ufjf.nenc.broadecos.api.Context;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import java.io.UnsupportedEncodingException;

import static java.net.URLDecoder.*;

public abstract class AbstractContextArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String TOKEN_HEADER = "broad-ecos-token";
    private static final String PLATFORM_HEADER = "broad-ecos-platform";

    protected Context buildContext(NativeWebRequest webRequest) {
        return Context.builder()
                .token(webRequest.getHeader(TOKEN_HEADER))
                .platform(decodeUrl(webRequest.getHeader(PLATFORM_HEADER)))
        .build();

    }

    private String decodeUrl(String url) {
        String decoded;
        try {
            decoded = decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return decoded;
    }
}
