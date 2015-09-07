package br.ufjf.nenc.thautology.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification extends Entity {

    @NotNull
    @NonNull
    @DBRef
    private User to;

    @NotNull
    @NonNull
    private Boolean seen;

    @NotNull
    @NonNull
    private String subject;

    @NotNull
    @NonNull
    private String message;

}
