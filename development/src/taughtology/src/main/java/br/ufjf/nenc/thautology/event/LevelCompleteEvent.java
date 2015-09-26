package br.ufjf.nenc.thautology.event;


import br.ufjf.nenc.broadecos.api.Context;
import br.ufjf.nenc.thautology.model.User;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public class LevelCompleteEvent {

    private final User user;

    private final Context context;

    private LevelCompleteEvent(User user, Context context){
        this.user = user;
        this.context = context;
    }

    public static LevelCompleteEvent from(User user,Context context) {
        return new LevelCompleteEvent(user, context);
    }


}
