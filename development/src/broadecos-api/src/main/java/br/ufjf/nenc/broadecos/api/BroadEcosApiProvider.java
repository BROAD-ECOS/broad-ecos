package br.ufjf.nenc.broadecos.api;

import br.ufjf.nenc.broadecos.api.model.Course;
import br.ufjf.nenc.broadecos.api.model.Metadata;
import br.ufjf.nenc.broadecos.api.model.PlatformInfo;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.Map;
import java.util.WeakHashMap;

import static br.ufjf.nenc.broadecos.api.util.PreconditionPredicates.isNotNull;
import static br.ufjf.nenc.broadecos.api.util.PreconditionPredicates.isNull;
import static com.google.common.base.Preconditions.checkArgument;

@Builder
public class BroadEcosApiProvider {

    @Data
    private class AuthRequestKey {
        private final String platform;
        private final String course;
    }

    private static final Map<AuthRequestKey, AuthRequest> AUTH_REQUESTS = new WeakHashMap<>();

    @NonNull
    private Metadata metadata;

    public BroadEcosApi withContext(Context context) {

        return new BroadEcosApiImpl(context);
    }

    public AuthRequest requestAuth(PlatformInfo platform, Course course) {
        return requestAuth(platform.getUrl(), course.getId());
    }

    public AuthRequest requestAuth(String platform, String course) {
        checkArgument(isNotNull(platform));
        checkArgument(isNotNull(course));

        final AuthRequestKey key = new AuthRequestKey(platform, course);
        AuthRequest authRequest = AUTH_REQUESTS.get(key);
        if (isNull(authRequest) || authRequest.isExpired()) {
            authRequest = new AuthRequest(metadata, platform, course);
            AUTH_REQUESTS.put(key, authRequest);
        }

        return authRequest;
    }


    public Context completeAuthRequest(String platform, String course, String code) {
        checkArgument(isNotNull(platform));
        checkArgument(isNotNull(course));
        checkArgument(isNotNull(code));

        final AuthRequestKey key = new AuthRequestKey(platform, course);
        AuthRequest authRequest = AUTH_REQUESTS.get(key);

        final Context context;
        if (isNotNull(authRequest) && !authRequest.isExpired()) {
            context = authRequest.createContext(code);
        } else {
            throw new IllegalStateException();
        }

        return context;

    }

}
