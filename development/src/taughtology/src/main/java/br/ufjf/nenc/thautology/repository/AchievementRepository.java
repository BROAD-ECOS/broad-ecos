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
    Iterable<Achievement> findAllByUser(User user);
}
