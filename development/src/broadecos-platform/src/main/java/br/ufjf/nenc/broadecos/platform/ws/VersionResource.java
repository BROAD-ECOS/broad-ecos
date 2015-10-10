package br.ufjf.nenc.broadecos.platform.ws;

import br.ufjf.nenc.broadecos.platform.api.Api;
import br.ufjf.nenc.broadecos.platform.util.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;

@RestController
@RequestMapping("/version")
public class VersionResource {

    private final Processor processor;

    @Autowired
    public VersionResource(Processor processor) {
        this.processor = processor;
    }


    @RequestMapping("/api")
    public Api getApi(){
        InputStream inputStream = getClass().getResourceAsStream("/br/ufjf/nenc/broadecos/platform/api/broadecos-api.xml");
        return processor.unmarshall(inputStream, Api.class);
    }

}
