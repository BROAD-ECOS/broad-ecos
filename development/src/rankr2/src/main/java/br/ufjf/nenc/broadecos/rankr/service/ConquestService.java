package br.ufjf.nenc.broadecos.rankr.service;

import br.ufjf.nenc.broadecos.rankr.model.Conquest;
import br.ufjf.nenc.broadecos.rankr.repository.ConquestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ConquestService {

    private final ConquestRepository conquestRepository;

    @Autowired
    public ConquestService(ConquestRepository conquestRepository) {
        this.conquestRepository = conquestRepository;
    }

    public Conquest save(Conquest conquest) {
        return conquestRepository.save(conquest);
    }

    public Page<Conquest> all(PageRequest pageRequest) {
        return conquestRepository.findAllByOrderByCreatedDesc(pageRequest);
    }
}
