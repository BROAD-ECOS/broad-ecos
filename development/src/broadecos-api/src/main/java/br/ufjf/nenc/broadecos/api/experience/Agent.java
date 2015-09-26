package br.ufjf.nenc.broadecos.api.experience;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class Agent extends Actor {

    public String getObjectType(){
        return "Agent";
    }

    @Override
    public void setObjectType(String type) {
        throw new UnsupportedOperationException();
    }
}
