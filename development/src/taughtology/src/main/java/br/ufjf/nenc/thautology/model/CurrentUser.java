package br.ufjf.nenc.thautology.model;

import br.ufjf.nenc.broadecos.Context;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;


@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CurrentUser implements Serializable {

    static final long serialVersionUID = 1L;

    private User user;

    private Context context;

}
