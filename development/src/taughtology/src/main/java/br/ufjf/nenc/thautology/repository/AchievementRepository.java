package br.ufjf.nenc.thautology.repository;

import br.ufjf.nenc.thautology.model.Achievement;
import br.ufjf.nenc.thautology.model.Answer;
import br.ufjf.nenc.thautology.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface AchievementRepository extends PagingAndSortingRepository<Achievement, String> {

    Optional<Achievement> findOneByRelatedAnswerId(String answerId);

    Iterable<Achievement> findOneByRelatedAnswerUser(User user);

    Iterable<Achievement> findAllByRelatedAnswerIdIn(Set<String> answerIds);
}
