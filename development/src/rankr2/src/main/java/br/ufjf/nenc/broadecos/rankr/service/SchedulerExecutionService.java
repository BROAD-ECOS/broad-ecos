package br.ufjf.nenc.broadecos.rankr.service;

import br.ufjf.nenc.broadecos.rankr.model.SchedulerExecution;
import br.ufjf.nenc.broadecos.rankr.repository.SchedulerExecutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.Optional;

import static java.lang.Boolean.TRUE;

@Service
public class SchedulerExecutionService {

    private final Pageable ONLY_FIRST = new PageRequest(0,1);

    private final SchedulerExecutionRepository schedulerExecutionRepository;

    @Autowired
    public SchedulerExecutionService(SchedulerExecutionRepository schedulerExecutionRepository) {
        this.schedulerExecutionRepository = schedulerExecutionRepository;
    }

    public Optional<SchedulerExecution> getLastSuccess(String type) {
        Optional<SchedulerExecution> lastSuccess = Optional.empty();

        Iterator<SchedulerExecution> executions = schedulerExecutionRepository
                .findAllByTypeAndSuccessOrderByCreatedDesc(type, TRUE, ONLY_FIRST)
                .iterator();

        if (executions.hasNext()) {
            lastSuccess = Optional.of(executions.next());
        }
        return lastSuccess;
    }

    public SchedulerExecution save(SchedulerExecution schedulerExecution) {
        schedulerExecution.setCreated(new Date());
        schedulerExecution.setLastUpdated(new Date());
        return schedulerExecutionRepository.save(schedulerExecution);
    }
}
