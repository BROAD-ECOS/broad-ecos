package br.ufjf.nenc.lp.core.repository;

import br.ufjf.nenc.lp.core.entity.Participant;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ParticipantRepository extends PagingAndSortingRepository<Participant, String> {

    @Query("{ $and: [{$or : [ { $where: '?0 == null' } , { 'id' : ?0 }]}, {$or : [ { $where: '?1 == null' } , { 'email' : ?1 }]}]}")
    List<Participant> query(String id, String email);

    Participant findOneByEmail(String email);
}



