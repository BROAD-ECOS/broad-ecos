package br.ufjf.nenc.thautology.repository;

import br.ufjf.nenc.broadecos.api.model.Course;
import br.ufjf.nenc.broadecos.api.model.ParticipantProfile;
import br.ufjf.nenc.thautology.model.Classmate;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassmateRepository extends PagingAndSortingRepository<Classmate, String>{

    Long deleteByUserParticipantProfileIdNotInAndCourseId(List<String> profilesToRetain, String course) ;

    Iterable<Classmate> findAllByCourseId(String course);
}
