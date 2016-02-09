package br.ufjf.nenc.broadecos.platform.service;

import br.ufjf.nenc.broadecos.platform.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final TokenRepository tokenRepository;

    @Autowired
    public AuthService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    
}
