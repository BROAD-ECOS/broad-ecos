package br.ufjf.nenc.lp.core.service;

import br.ufjf.nenc.lp.core.entity.Participant;
import br.ufjf.nenc.lp.core.repository.ParticipantRepository;
import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;

@Service
public class ParticipantService {

    private final ParticipantRepository repository;

    @Autowired
    public ParticipantService(ParticipantRepository repository) {
        this.repository = repository;
    }

    public Participant save(Participant descriptor) {
        return repository.save(descriptor);
    }

    public List<Participant> findBy(@Nullable String id, @Nullable String email) {
        return repository.query(id, email);
    }

    public Participant findOneByEmail(String email) {
        return repository.findOneByEmail(email);
    }

    public Participant findOrCreateByEmailAndName(String email, String name) {

        Optional<Participant> participant = Optional.fromNullable(findOneByEmail(email));

        Supplier<Participant> supplier = () -> createWithEmail(email, name);

        return  participant.or(supplier);
    }

    private Participant createWithEmail(String email, String name) {
        Participant participant = new Participant();
        participant.setEmail(email);
        participant.setName(name);
        participant = save(participant);
        return participant;
    }
}
