package br.ufjf.nenc.broadecos.auth.model.bo;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {

    private final String authorityName;

    public Authority(String authorityName) {
        this.authorityName = authorityName;
    }

    @Override
    public String getAuthority() {
        return null;
    }

    @Override
    public String toString() {
        return "Authority{" +
                "authorityName='" + authorityName + '\'' +
                '}';
    }
}
