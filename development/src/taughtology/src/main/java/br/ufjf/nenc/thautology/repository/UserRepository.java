package br.ufjf.nenc.thautology.repository;

import br.ufjf.nenc.thautology.model.Question;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<Question, String>{

}
