package br.ufjf.nenc.broadecos.api.experience;

import lombok.*;

import java.net.URI;

@Builder
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class ExperienceRequest {

    private final URI verbId;

}
