package br.ufjf.nenc.broadecos.platform.model;

import br.ufjf.nenc.broadecos.platform.api.Param;
import br.ufjf.nenc.broadecos.platform.excp.PlatformException;
import br.ufjf.nenc.broadecos.platform.util.ReadString;
import com.google.common.collect.ImmutableMap;
import javaslang.control.Try;
import lombok.Getter;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

@Getter
public class RequestParams {

    private final Map<String, String[]> params;

    private final String body;

    public static RequestParams from(ServletRequest serverRequest) {
        return Try.of(() -> {
            Map<String, String[]> params = ImmutableMap.copyOf(serverRequest.getParameterMap());
            String body = new ReadString(serverRequest.getReader()).toString();
            return new RequestParams(params, body);
        }).recover(PlatformException::reThrow)
       .get();

    }

    private RequestParams(Map<String, String[]> params, String body) {
        this.params = params;
        this.body = body;
    }

    public Map<String, String> getParamsFlat() {
        ImmutableMap.Builder<String, String> flatParams = ImmutableMap.builder();
        params.entrySet().forEach(e -> flatParams.put(e.getKey(), String.join(",", e.getValue())));
        return flatParams.build();
    }

}
