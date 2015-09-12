package br.ufjf.nenc.lp.core.pl;

import com.google.common.base.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuilderHolder {

    @Autowired
    private List<Builder> builders;

    public Optional<Builder> getResponsibleBuilder(Feature feature) {

        Builder responsibleBuilder = null;
        for (Builder builder : builders) {
            if (builder.isResponsibleFor(feature)) {
                responsibleBuilder = builder;
                break;
            }
        }
        return Optional.fromNullable(responsibleBuilder);
    }
}
