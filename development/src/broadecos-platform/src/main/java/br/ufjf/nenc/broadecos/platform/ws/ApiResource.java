package br.ufjf.nenc.broadecos.platform.ws;

import br.ufjf.nenc.broadecos.platform.api.Method;
import br.ufjf.nenc.broadecos.platform.model.ApiRequest;
import br.ufjf.nenc.broadecos.platform.model.RequestParams;
import br.ufjf.nenc.broadecos.platform.model.TokenContext;
import br.ufjf.nenc.broadecos.platform.service.ApiRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/**")
public class ApiResource {

    private static final String API_BASE_PATH = "/api";

    private final ApiRequestHandler apiRequestHandler;

    @Autowired
    public ApiResource(ApiRequestHandler apiRequestHandler) {
        this.apiRequestHandler = apiRequestHandler;
    }

    @RequestMapping(method = GET)
    public Object get(final HttpServletRequest request){

        ApiRequest apiRequest = ApiRequest.builder()
                .method(Method.GET)
                .path(request.getServletPath().substring(API_BASE_PATH.length()))
                .tokenContext(new TokenContext())
                .params(RequestParams.from(request))
                .build();

        return apiRequestHandler.handle(apiRequest);
    }

    @RequestMapping(method = POST)
    public Object post(final HttpServletRequest request){
        return new HashMap<>();
    }

}
