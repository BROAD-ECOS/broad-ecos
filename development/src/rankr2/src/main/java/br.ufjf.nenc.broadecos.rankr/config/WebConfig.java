package br.ufjf.nenc.broadecos.rankr.config;

import br.ufjf.nenc.broadecos.rankr.provider.CurrentCourseProvider;
import br.ufjf.nenc.broadecos.rankr.provider.CurrentUserProvider;
import br.ufjf.nenc.broadecos.rankr.resolver.ContextArgumentResolver;
import br.ufjf.nenc.broadecos.rankr.resolver.CurrentCourseArgumentResolver;
import br.ufjf.nenc.broadecos.rankr.resolver.CurrentUserArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.Filter;
import java.util.List;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private CurrentUserProvider currentUserProvider;

    @Autowired
    private CurrentCourseProvider currentCourseProvider;


    @Bean
    public ContextArgumentResolver contextArgumentResolver(){
        return new ContextArgumentResolver();
    }

    @Bean
    public CurrentUserArgumentResolver currentUserArgumentResolver(){
        return new CurrentUserArgumentResolver(currentUserProvider);
    }

    @Bean
    public CurrentCourseArgumentResolver currentCourseArgumentResolver(){
        return new CurrentCourseArgumentResolver(currentCourseProvider);
    }

    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }

    @Bean
    public Filter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(contextArgumentResolver());
        argumentResolvers.add(currentUserArgumentResolver());
        argumentResolvers.add(currentCourseArgumentResolver());
    }
}
