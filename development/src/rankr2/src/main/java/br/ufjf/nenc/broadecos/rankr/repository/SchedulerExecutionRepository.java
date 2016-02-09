package br.ufjf.nenc.broadecos.rankr.repository;

import br.ufjf.nenc.broadecos.rankr.model.SchedulerExecution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  SchedulerExecutionRepository  extends PagingAndSortingRepository<SchedulerExecution, String> {

    Page<SchedulerExecution> findAllByTypeAndSuccessOrderByCreatedDesc(String  type, Boolean success, Pageable pageRequest);
}
