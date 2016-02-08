package br.ufjf.nenc.broadecos.api.experience;

import br.ufjf.nenc.broadecos.api.util.ISO8601Date;
import com.google.common.collect.ImmutableList;
import lombok.*;

import java.lang.*;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Builder
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class ExperienceRequest {

    @Getter
    @EqualsAndHashCode
    public static class Param {
        private String name;
        private String value;

        private Param (String name, java.lang.Object value) {
            this.name = name;
            if (value!=null){
                this.value = value.toString();
            }
        }

        public Boolean isEmpty(){
            return value!=null;
        }
    }

    private final URI verbId;

    private final LocalDateTime since;

    public Set<Param> params() {
        return ImmutableList.<Param> builder()
                .add(new Param("since", new ISO8601Date(since)))
                        .add(new Param("verb", verbId))
                        .build().stream()
                        .filter(Param::isEmpty)
                        .collect(toSet());

    }
}
