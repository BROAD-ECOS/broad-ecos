package br.ufjf.nenc.lp.core.repository;

import br.ufjf.nenc.lp.core.entity.LearningObject;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LearningObjectRepository extends PagingAndSortingRepository<LearningObject, String> {

}
