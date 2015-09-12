package br.ufjf.nenc.lp.core.service;

import br.ufjf.nenc.lp.core.bo.AcquiredConquest;
import br.ufjf.nenc.lp.core.entity.Conquest;
import br.ufjf.nenc.lp.core.entity.Participant;
import br.ufjf.nenc.lp.core.entity.Profile;
import br.ufjf.nenc.lp.core.repository.ConquestRepository;
import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ConquestService {

    private final ParticipantService participantService;

    private final ProfileService profileService;

    private final ConquestRepository repository;

    @Autowired
    public ConquestService(ConquestRepository repository, ParticipantService participantService, ProfileService profileService) {

        this.repository = repository;
        this.participantService = participantService;
        this.profileService = profileService;
    }

    public Conquest save(AcquiredConquest acquired) {

        Participant participant = participantService.findOrCreateByEmailAndName(acquired.getParticipantBizId(), acquired.getParticipantName());
        Profile profile = profileService.findOrCreateByParticipant(participant);
        Conquest conquest = updateOrCreateConquestToProfile(acquired, profile);
        profileService.save(profile);
        conquest = repository.save(conquest);

        return conquest;
    }

    private Conquest updateOrCreateConquestToProfile(AcquiredConquest acquired, Profile profile) {

        Conquest conquest = repository.findOneByProfileIdAndLearningObjectIdAndInteractionAndType(profile.getId(),
                acquired.getLearningObjectId(),
                acquired.getInteraction(),
                acquired.getConquestType()
        );
        Optional<Conquest> conquestOptional = Optional.fromNullable(conquest);
        Supplier<Conquest> createByAcquiredConquestIntoProfile = () -> createByAcquiredConquestIntoProfile(acquired, profile);
        conquest = conquestOptional.or(createByAcquiredConquestIntoProfile);
        updateScore(profile, acquired, conquest);

        return conquest;
    }

    private void updateScore(Profile profile, AcquiredConquest acquired, Conquest conquest) {

        if (conquest.getType().isGreater(acquired.getValue(), conquest.getValue())) {
            profile.updateSummary(conquest, acquired.getValue());
            conquest.setValue(acquired.getValue());
            conquest.setUpdatedAt(new Date());
        }
    }

    private Conquest createByAcquiredConquestIntoProfile(AcquiredConquest acquired, Profile profile) {

        Conquest conquest = assembleAcquiredConquest(acquired);
        conquest.setProfileId(profile.getId());
        profile.summarize(conquest);

        return conquest;
    }

    private Conquest assembleAcquiredConquest(AcquiredConquest acquired) {

        Conquest conquest = new Conquest();
        conquest.setLearningObjectId(acquired.getLearningObjectId());
        conquest.setInteraction(acquired.getInteraction());
        conquest.setType(acquired.getConquestType());
        conquest.setValue(acquired.getValue());
        conquest.setInteraction(acquired.getInteraction());
        conquest.setCreatedAt(new Date());
        conquest.setUpdatedAt(new Date());

        return conquest;
    }


    public List<Conquest> findAllByProfileId(String profileId) {
        return repository.findAllByProfileId(profileId);
    }
}
