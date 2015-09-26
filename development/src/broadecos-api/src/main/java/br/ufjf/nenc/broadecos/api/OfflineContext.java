package br.ufjf.nenc.broadecos.api;

import br.ufjf.nenc.broadecos.api.model.Course;
import br.ufjf.nenc.broadecos.api.model.PlatformInfo;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class OfflineContext {

    private final String AUTHORIZE_ENDPOINT = "/auth/authorize";

    private final PlatformInfo platformInfo;

    private final Course course;

    private final AtomicReference<Token> token = new AtomicReference<>();

    private final AtomicReference<Code> code = new AtomicReference<>();


    OfflineContext(PlatformInfo platformInfo, Course course) {
        this.platformInfo = platformInfo;
        this.course = course;
    }


    public boolean isConnected() {
        Optional<Token> theToken = Optional.ofNullable(token.get());
        return theToken.isPresent() && theToken.get().isValid();
    }

    public void requestConnection() {

        // Solicita Código
        // guarda código
        // Aramazena
    }

    public void connect(Code code) {

    }

    public boolean acceptCode() {
        return false;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OfflineContext that = (OfflineContext) o;
        return Objects.equals(platformInfo, that.platformInfo) &&
                Objects.equals(course, that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(platformInfo, course);
    }
}
