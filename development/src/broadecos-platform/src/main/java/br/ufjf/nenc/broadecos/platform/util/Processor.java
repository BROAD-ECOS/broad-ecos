package br.ufjf.nenc.broadecos.platform.util;

import br.ufjf.nenc.broadecos.platform.excp.PlatformException;
import javaslang.control.Try;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;

import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;

public class Processor {

    private final Marshaller marshaller;
    private final Unmarshaller unmarshaller;

    public Processor(Marshaller marshaller, Unmarshaller unmarshaller) {
        this.marshaller = marshaller;
        this.unmarshaller = unmarshaller;
    }

    @SuppressWarnings("unchecked")
    public <T> T unmarshall(InputStream inputStream, Class<T> apiClass) {
        return (T) Try.of(()-> unmarshaller.unmarshal(new StreamSource(inputStream)))
                .recover((e)-> {throw new PlatformException(e);})
                .get();
    }
}
