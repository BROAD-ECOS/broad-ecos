package br.ufjf.nenc.thautology.repository;

import br.ufjf.nenc.thautology.model.Challenge;
import br.ufjf.nenc.thautology.model.Question;
import br.ufjf.nenc.thautology.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChallengeRepository extends PagingAndSortingRepository<Challenge, String> {

    Optional<Challenge> findOneByChallengerAndQuestion(User challenger, Question question);
}
