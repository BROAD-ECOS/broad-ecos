package br.ufjf.nenc.broadecos.rankr.repository;

import br.ufjf.nenc.broadecos.rankr.model.Conquest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConquestRepository extends PagingAndSortingRepository<Conquest, String> {


    Long countByUserId(String userId);

    Page<Conquest> findAllByOrderByCreatedDesc(Pageable pageable);

    Page<Conquest> findAllByUserIdOrderByCreatedDesc(String userId, Pageable pageRequest);
}
