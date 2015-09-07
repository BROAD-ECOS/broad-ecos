package br.ufjf.nenc.broadecos.experience;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Score score;
    private Boolean success;
    private Boolean completion;
    private String response;
    private String duration;
}
