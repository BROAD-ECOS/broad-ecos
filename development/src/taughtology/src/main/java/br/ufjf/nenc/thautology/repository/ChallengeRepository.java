package br.ufjf.nenc.thautology.repository;

import br.ufjf.nenc.thautology.model.Challenge;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeRepository extends PagingAndSortingRepository<Challenge, String> {

}
