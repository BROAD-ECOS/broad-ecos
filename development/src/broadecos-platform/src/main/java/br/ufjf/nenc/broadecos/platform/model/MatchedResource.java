package br.ufjf.nenc.broadecos.platform.model;

import br.ufjf.nenc.broadecos.platform.api.Param;
import br.ufjf.nenc.broadecos.platform.api.Resource;
import lombok.Data;

import java.util.Map;

@Data
public class MatchedResource {

    private final Resource resource;

    private final Map<String, String> urlParams;

    public boolean hasUrlParam(Param p) {
        return urlParams.containsKey(p.getName());
    }
}
