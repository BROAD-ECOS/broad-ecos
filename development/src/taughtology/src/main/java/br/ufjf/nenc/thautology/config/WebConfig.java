package br.ufjf.nenc.thautology.config;

import br.ufjf.nenc.thautology.provider.CurrentCourseProvider;
import br.ufjf.nenc.thautology.provider.CurrentUserProvider;
import br.ufjf.nenc.thautology.resolver.ContextArgumentResolver;
import br.ufjf.nenc.thautology.resolver.CurrentCourseArgumentResolver;
import br.ufjf.nenc.thautology.resolver.CurrentUserArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

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

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(contextArgumentResolver());
        argumentResolvers.add(currentUserArgumentResolver());
        argumentResolvers.add(currentCourseArgumentResolver());
    }
}