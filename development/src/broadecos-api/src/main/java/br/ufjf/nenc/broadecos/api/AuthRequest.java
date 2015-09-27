package br.ufjf.nenc.broadecos.api;

import br.ufjf.nenc.broadecos.api.model.Metadata;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static br.ufjf.nenc.broadecos.api.util.PreconditionPredicates.isNotNull;
import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;
import static java.time.LocalDate.now;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.Collections.emptyMap;


public class AuthRequest {

    private static final String AUTH_AUTHORIZE_URL_PATTERN = "/auth/authorize" +
            "?client_id=%s" +
            "&course_id=%s" +
            "&redirect_uri=%s" +
            "&response_type=code";

    private static final String AUTH_TOKEN_URL_PATTERN = "/auth/token" +
            "?client_id=%s" +
            "&course_id=%s" +
            "&code=%s";

    private final WSClient wsClient = new WSClient();

    private final LocalDate codeRequested = now();

    private final String platform;

    private final String course;

    private final String serviceId;

    private final String redirectUri;
    private List<Consumer<BroadEcosApi>> consumers =  new ArrayList<>();

    AuthRequest(Metadata metadata, String platform, String course) {
        this.platform = platform;
        this.serviceId = metadata.getId();
        this.redirectUri = metadata.getRedirectURI();
        this.course = course;

        loadCode();
    }

    private void loadCode() {
        wsClient.get(platform, format(AUTH_AUTHORIZE_URL_PATTERN, serviceId, course, redirectUri), Code.class, emptyMap());

    }

    public Context createContext(String code) {
        checkArgument(isNotNull(code));

        Token token = wsClient.get(platform, format(AUTH_TOKEN_URL_PATTERN, serviceId, course, code), Token.class, emptyMap());

        final Context context = new Context(token.getToken(), platform);

        BroadEcosApi api = new BroadEcosApiImpl(context);
        consumers.forEach(c -> c.accept(api));

        return context;
    }

    public boolean isExpired() {
        return codeRequested.isBefore(now().minus(5, MINUTES));
    }

    public void whenReady(Consumer<BroadEcosApi> broadEcosApiConsumer){
        this.consumers.add(broadEcosApiConsumer);
    }

}
