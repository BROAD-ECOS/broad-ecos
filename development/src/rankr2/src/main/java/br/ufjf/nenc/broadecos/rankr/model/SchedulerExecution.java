package br.ufjf.nenc.broadecos.rankr.model;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@ToString
public class SchedulerExecution extends Entity  {

    private String type;

    private Map<String, String> params;

    private Boolean success;


}
