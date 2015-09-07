package br.ufjf.nenc.thautology.repository;

import br.ufjf.nenc.thautology.model.Achievement;
import br.ufjf.nenc.thautology.model.AnswerAchievement;
import br.ufjf.nenc.thautology.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerAchievementRepository extends PagingAndSortingRepository<AnswerAchievement, String> {
}
