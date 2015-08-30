package br.ufjf.nenc.broadecos.model;


public enum Scope {
    PARTICIPANT_PROFILE("participant.profile"),
    PARTICIPANT_EMAIL("participant.email"),
    COURSES_CURRENT("courses.current"),
    COURSES_CURRENT_PARTICIPANTES("courses.current.participants");

    private final String name;

    Scope(String name) {
        this.name = name;
    }

    public String scope() {
        return name;
    }
}
