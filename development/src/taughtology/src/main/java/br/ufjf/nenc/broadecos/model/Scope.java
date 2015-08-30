package br.ufjf.nenc.broadecos.model;


public enum Scope {
    PARTICIPANT_PROFILE("participant.profile"),
    PARTICIPANT_EMAIL("participant.email");

    private final String name;

    Scope(String name) {
        this.name = name;
    }

    public String scope() {
        return name;
    }
}
