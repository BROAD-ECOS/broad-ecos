package br.ufjf.nenc.thautology.repository;

import br.ufjf.nenc.thautology.model.Question;
import br.ufjf.nenc.thautology.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, String>{

    Optional<User> findByParticipantId(String participantId);
}
