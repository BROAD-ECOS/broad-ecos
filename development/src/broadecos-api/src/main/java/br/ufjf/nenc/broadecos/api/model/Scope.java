package br.ufjf.nenc.broadecos.api.model;


public enum Scope {
    PARTICIPANT_PROFILE("participant.profile"),
    PARTICIPANT_EMAIL("participant.email"),
    COURSES_CURRENT("courses.current"),
    COURSES_CURRENT_PARTICIPANTES("courses.current.participants"),
    EXPERIENCE_WRITE("experiences.write"),
    EXPERIENCES_READ("experiences.read"),
    OFFLINE_ACCESS("offlineaccess");

    private final String name;

    Scope(String name) {
        this.name = name;
    }

    public String scope() {
        return name;
    }
}
