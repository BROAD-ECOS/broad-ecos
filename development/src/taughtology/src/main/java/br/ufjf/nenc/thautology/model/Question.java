package br.ufjf.nenc.thautology.model;


import com.google.common.collect.BiMap;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question extends Entity implements Serializable {

    private static final long serialVersionUID = 1L;

    @NonNull
    private String title;

    @NonNull
    private String content;

    private String image;

    @NonNull
    private Boolean solution;

    @NonNull
    private Level level;


    public String getLevelName(Locale locale){
        return level.getLocalNameOrDefault(locale);
    }
}
