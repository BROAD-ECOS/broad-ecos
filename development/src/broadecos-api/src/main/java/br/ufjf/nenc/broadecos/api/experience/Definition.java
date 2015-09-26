package br.ufjf.nenc.broadecos.api.experience;

import lombok.*;

import java.net.URI;
import java.util.Map;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Definition {
    private Map<String, String> name;
    private Map<String, String> description;
    private URI type;
    private URI moreInfo;
}
