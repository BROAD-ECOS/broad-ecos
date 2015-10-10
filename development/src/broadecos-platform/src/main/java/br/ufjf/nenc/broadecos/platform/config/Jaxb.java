package br.ufjf.nenc.broadecos.platform.config;

import br.ufjf.nenc.broadecos.platform.util.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class Jaxb {

    public static final String PROJECT_PACKAGE = "br.ufjf.nenc";

    @Bean
    public Processor getCastorMarshaller() {
        final Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setPackagesToScan(PROJECT_PACKAGE);
        jaxb2Marshaller.setMarshallerProperties(getProperties());

        return new Processor(jaxb2Marshaller, jaxb2Marshaller);
    }

    private Map<String, Object> getProperties() {
        Map<String,Object> map = new HashMap<>();
        map.put("jaxb.formatted.output", true);
        return map;
    }
}
