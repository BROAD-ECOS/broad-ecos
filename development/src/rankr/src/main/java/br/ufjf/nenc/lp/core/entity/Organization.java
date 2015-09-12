package br.ufjf.nenc.lp.core.entity;

public class Organization {

    private String bizId;

    private String name;

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String id) {
        this.bizId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Organization{");
        sb.append("bizId='").append(bizId).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
