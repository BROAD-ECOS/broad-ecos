package br.ufjf.nenc.broadecos.api.model;

import lombok.Data;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

@Data
public class Icon {
    private final String small;
    private final String big;

    public Icon(Optional<String> small, Optional<String> big){
        checkArgument(small.isPresent() || big.isPresent());
        this.small = small.orElse(null);
        this.big = big.orElse(null);
    }
}
