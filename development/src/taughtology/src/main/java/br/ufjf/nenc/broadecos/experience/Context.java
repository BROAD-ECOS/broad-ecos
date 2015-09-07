package br.ufjf.nenc.broadecos.experience;

import lombok.*;

import java.util.Map;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Context {
    private String platform;
    private Map<String, Object> extensions;
}
