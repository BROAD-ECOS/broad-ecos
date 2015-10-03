package br.ufjf.nenc.broadecos.rankr.service;

import br.ufjf.nenc.broadecos.rankr.model.Conquest;
import br.ufjf.nenc.broadecos.rankr.model.ConquestCount;
import br.ufjf.nenc.broadecos.rankr.repository.ConquestRepository;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

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

    public ConquestCount count(String userId) {
        checkArgument(userId != null);
        return new ConquestCount(conquestRepository.countByUserId(userId));
    }

    public Page<Conquest> all(Optional<String> userId, PageRequest pageRequest) {
        final Page<Conquest> conquests;

        if (userId.isPresent()) {
            conquests = allById(userId.get(), pageRequest);
        } else {
            conquests = all(pageRequest);
        }
        return conquests;
    }

    public Page<Conquest> all(PageRequest pageRequest) {
        return conquestRepository.findAllByOrderByCreatedDesc(pageRequest);
    }

    private Page<Conquest> allById(String userId, PageRequest pageRequest) {
        return conquestRepository.findAllByUserIdOrderByCreatedDesc(userId, pageRequest);
    }
}

