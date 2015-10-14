package br.ufjf.nenc.broadecos.platform.util;

import br.ufjf.nenc.broadecos.platform.excp.PlatformException;
import javaslang.control.Try;

import java.io.Reader;
import java.io.StringWriter;

public class ReadString {

    private final Reader reader;

    public ReadString(Reader reader) {
        this.reader = reader;
    }

    @Override
    public String toString() {
        return Try.of( ()-> {
            StringWriter sw = new StringWriter();
            char[] buffer = new char[1024 * 4];
            int n = 0;
            while (-1 != (n = reader.read(buffer))) {
                sw.write(buffer, 0, n);
            }
            return sw.toString();
        }).recover(e -> {
            throw new PlatformException(e);
        }).get();
    }
}
