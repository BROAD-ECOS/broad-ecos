package br.ufjf.nenc.lp.core.scorm;

public class Resource {

    private final String href;

    Resource(String href) {
        this.href = href;
    }

    public String getHref() {
        return href;
    }

    @Override
    public String toString() {
        return String.format("{Resource href: '%s'}", getHref());
    }
}
