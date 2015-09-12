package br.ufjf.nenc.lp.core.scorm;

public class Organization {

    private final String id;

    private final String name;

    Organization(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("{Organization id: '%s' name:'%s'}", getId(), getName());
    }
}
