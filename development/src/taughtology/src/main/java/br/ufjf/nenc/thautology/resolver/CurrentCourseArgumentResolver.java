package br.ufjf.nenc.thautology.resolver;

import br.ufjf.nenc.broadecos.api.Context;
import br.ufjf.nenc.broadecos.api.model.Course;
import br.ufjf.nenc.thautology.provider.CurrentCourseProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;


public class CurrentCourseArgumentResolver extends AbstractContextArgumentResolver {

    private final CurrentCourseProvider currentCourseProvider;

    @Autowired
    public CurrentCourseArgumentResolver(CurrentCourseProvider currentCourseProvider) {
        this.currentCourseProvider = currentCourseProvider;
    }


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(Course.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) throws Exception {

        Context context = buildContext(webRequest);
        return currentCourseProvider.currentCourse(context);
    }

}
