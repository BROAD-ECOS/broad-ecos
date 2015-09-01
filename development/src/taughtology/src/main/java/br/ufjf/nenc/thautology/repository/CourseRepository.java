package br.ufjf.nenc.thautology.repository;

import br.ufjf.nenc.broadecos.model.Course;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends PagingAndSortingRepository<Course, String> {

}
