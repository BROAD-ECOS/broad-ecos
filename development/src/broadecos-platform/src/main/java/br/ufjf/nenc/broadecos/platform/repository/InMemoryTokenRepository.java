package br.ufjf.nenc.broadecos.platform.repository;

import br.ufjf.nenc.broadecos.api.Token;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryTokenRepository  implements TokenRepository{

    private final ConcurrentHashMap<String,Token> tokens = new ConcurrentHashMap<>();


    @Override
    public void store(Token token) {
        Preconditions.checkArgument(token!=null);

        tokens.put(token.getToken(), token);
    }

    @Override
    public Optional<Token> retrieve(String token) {
        Preconditions.checkArgument(token!=null);

        return Optional.ofNullable(tokens.get(token));
    }
}
