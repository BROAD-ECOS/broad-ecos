package br.ufjf.nenc.thautology.component;

import br.ufjf.nenc.broadecos.api.Context;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Scope(scopeName="request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@ToString
public class BroadContext {

    private static final String TOKEN_HEADER = "broad-ecos-token";
    private static final String PLATFORM_HEADER = "broad-ecos-platform";

    private final Context context;

    @Autowired
    public BroadContext(HttpServletRequest request) {
        System.out.print(request);
        this.context =Context.builder()
                .token(request.getHeader(TOKEN_HEADER))
                .platform(request.getHeader(PLATFORM_HEADER))
                .build();
    }

    public Context get() {
        return context;
    }
}
