package br.ufjf.nenc.eenrollment.provider;

import br.ufjf.nenc.broadecos.api.model.Metadata;
import br.ufjf.nenc.broadecos.api.model.RequestedExtensions;
import br.ufjf.nenc.broadecos.api.model.RequestedScope;
import br.ufjf.nenc.broadecos.api.model.Scope;
import com.google.common.collect.ImmutableSet;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import static java.lang.Boolean.*;

@Component
public class MetadataProvider {

    @Bean
    public Metadata metadata() {
        return Metadata.builder()
                .id("e-enrollment")
                .name("e-Enrollment")
                .description("A enrollment tool.")
                .entryPoint("http://dev.broadecos:8081/index.html")
                .scope(new RequestedScope(Scope.COURSES_CURRENT, TRUE, "Read current course information."))
                .extension(new RequestedExtensions(
                        "http://www.example.org/x-enroll",
                        ImmutableSet.of(new RequestedScope("enroll.write", TRUE, "Do enrollment")),
                        "Do enrollment"
                )).build();
    }


}
