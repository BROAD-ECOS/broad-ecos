package br.ufjf.nenc.thautology.repository;

import br.ufjf.nenc.broadecos.model.Course;
import br.ufjf.nenc.broadecos.model.ParticipantProfile;
import br.ufjf.nenc.thautology.model.Classmate;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassmateRepository extends PagingAndSortingRepository<Classmate, String>{

    Long deleteByUserParticipantProfileNotInAndCourse(List<ParticipantProfile> profilesToRetain, Course course) ;

    Iterable<Classmate> findAllByCourse(Course course);
}
