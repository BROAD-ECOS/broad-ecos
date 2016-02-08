package br.ufjf.nenc.broadecos.api.experience;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ExperienceResponse {

    private String more;

    private List<ExperienceStatement> statements;

}
