package br.ufjf.nenc.thautology.model;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Option extends Entity {

    private String content;
}
