package br.ufjf.nenc.broadecos.platform.repository;

import br.ufjf.nenc.broadecos.api.Token;

import java.util.Optional;

public interface TokenRepository {

    void store(Token token);

    Optional<Token> retrieve(String token);

}
