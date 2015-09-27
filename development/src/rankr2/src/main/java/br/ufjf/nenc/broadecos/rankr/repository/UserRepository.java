package br.ufjf.nenc.broadecos.rankr.repository;

import br.ufjf.nenc.broadecos.rankr.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, String> {

    Optional<User> findByParticipantId(String participantId);
}
