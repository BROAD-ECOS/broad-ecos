package br.ufjf.nenc.broadecos.api.experience;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Score {
    private Double scaled;
    private Double raw;
    private Double min;
    private Double max;
}
