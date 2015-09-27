package br.ufjf.nenc.broadecos.api.experience;

import br.ufjf.nenc.broadecos.api.util.ISO8601Date;
import lombok.*;

import java.util.Date;

import static javax.xml.bind.DatatypeConverter.*;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ExperienceStatement {

    private String id;

    @NonNull
    private Actor actor;

    @NonNull
    private Verb verb;

    @NonNull
    private Object object;

    private Context context;

    private Result result;

    private Actor authority;

    private String version;

    private String stored;

    private String timestamp;

    public Date getStored(){
        Date storedDate = null;
        if (stored !=null) {
            storedDate = new ISO8601Date(stored).asDate();
        }
       return storedDate;
    }

    public Date getTimestamp(){
        Date timestampDate = null;
        if (timestamp !=null) {
            timestampDate = new ISO8601Date(timestamp).asDate();
        }
        return timestampDate;
    }


}
