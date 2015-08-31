package br.ufjf.nenc.thautology.resolver;

import br.ufjf.nenc.broadecos.Context;
import br.ufjf.nenc.thautology.model.CurrentUser;
import br.ufjf.nenc.thautology.provider.CurrentUserProvider;
import br.ufjf.nenc.thautology.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;


public class CurrentUserArgumentResolver extends AbstractContextArgumentResolver {

    private final CurrentUserProvider currentUserProvider;

    @Autowired
    public CurrentUserArgumentResolver(CurrentUserProvider currentUserProvider) {
        this.currentUserProvider = currentUserProvider;
    }


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) throws Exception {

        Context context = buildContext(webRequest);
        return currentUserProvider.currentUser(context);
    }

}