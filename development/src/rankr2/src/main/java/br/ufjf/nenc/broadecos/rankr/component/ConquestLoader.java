package br.ufjf.nenc.broadecos.rankr.component;

import br.ufjf.nenc.broadecos.api.BroadEcosApi;
import br.ufjf.nenc.broadecos.api.experience.*;
import br.ufjf.nenc.broadecos.api.model.ParticipantProfile;
import br.ufjf.nenc.broadecos.rankr.model.Conquest;
import br.ufjf.nenc.broadecos.rankr.model.SchedulerExecution;
import br.ufjf.nenc.broadecos.rankr.model.User;
import br.ufjf.nenc.broadecos.rankr.service.ConquestService;
import br.ufjf.nenc.broadecos.rankr.service.SchedulerExecutionService;
import br.ufjf.nenc.broadecos.rankr.service.UserService;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static br.ufjf.nenc.broadecos.rankr.util.DateUtil.from;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Component
public class ConquestLoader {

    private static final String SCHEDULER_TYPE = ConquestLoader.class.getName();

    private static final List<Verb> verbs = ImmutableList. <Verb> builder()
            .add(Verbs.WON.bean())
            .add(new Verb(URI.create("http://wordnet-rdf.princeton.edu/wn31/202294200-v"), null))
            .build();

    private final SchedulerExecutionService schedulerExecutionService;

    private final UserService userService;

    private final ConquestService conquestService;

    @Autowired
    public ConquestLoader(SchedulerExecutionService schedulerExecutionService, UserService userService, ConquestService conquestService) {
        this.schedulerExecutionService = schedulerExecutionService;
        this.userService = userService;
        this.conquestService = conquestService;
    }

    public synchronized void load(BroadEcosApi broadEcosApi) {

        final LocalDateTime since = getSinceParam();

        try {
            verbs.forEach((verb) -> {
                final ExperienceResponse response = broadEcosApi.getExperience(ExperienceRequest.builder()
                                .verbId(verb.getId())
                                .since(since)
                                .build()
                );

                response.getStatements().forEach((stmt) -> {

                    final String id = stmt.getActor().getAccount().getName();

                    final Optional<ParticipantProfile> participantProfile = broadEcosApi.getParticipant(id);

                    if (participantProfile.isPresent()) {
                        final User user = userService.retriveOrCreateUser(participantProfile.get());
                        createConquestForUser(stmt, user);
                    }
                });
            });

            createSuccessExecution();

        } catch (Exception e) {
            createFailureExecution();
            e.printStackTrace();
            throw e;
        }
    }

    private void createFailureExecution() {
        createExecution(FALSE);
    }

    private void createSuccessExecution() {
        createExecution(TRUE);
    }

    private void createExecution(Boolean success) {
        SchedulerExecution schedulerExecution = new SchedulerExecution();
        schedulerExecution.setType(SCHEDULER_TYPE);
        schedulerExecution.setSuccess(success);
        schedulerExecutionService.save(schedulerExecution);
    }

    private Conquest createConquestForUser(ExperienceStatement stmt, User user) {

        final Conquest conquest = new Conquest();
        conquest.setStatement(stmt);

        user.addPoints(conquest.getPoints());
        conquest.setUser(user);

        return conquestService.save(conquest);
    }

    private LocalDateTime getSinceParam() {
        final Optional<SchedulerExecution> schedulerExecution = schedulerExecutionService.getLastSuccess(SCHEDULER_TYPE);
        final LocalDateTime lastSuccessExecutionTime;
        if (schedulerExecution.isPresent()) {
            lastSuccessExecutionTime = from(schedulerExecution.get().getCreated());
        } else {
            lastSuccessExecutionTime = null;
        }
        return lastSuccessExecutionTime;
    }

}
