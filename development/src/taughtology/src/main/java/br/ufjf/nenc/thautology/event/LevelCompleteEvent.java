package br.ufjf.nenc.thautology.event;


import br.ufjf.nenc.thautology.model.User;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public class LevelCompleteEvent {


    private final User user;


    private LevelCompleteEvent(User user){
        this.user = user;
    }

    public static LevelCompleteEvent from(User user) {
        return new LevelCompleteEvent(user);
    }


}
