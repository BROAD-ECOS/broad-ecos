package br.ufjf.nenc.lp.core.service;

import br.ufjf.nenc.lp.core.entity.Participant;
import br.ufjf.nenc.lp.core.entity.Profile;
import br.ufjf.nenc.lp.core.repository.ProfileRepository;
import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class ProfileService {

    private final ProfileRepository repository;

    @Autowired
    public ProfileService(ProfileRepository repository) {
        this.repository = repository;
    }

    public Profile findOrCreateByParticipant(Participant participant) {

        Optional<Profile> profile = Optional.fromNullable(repository.findOneByParticipantEmail(participant.getEmail()));

        Supplier<Profile> createByParticipant = () -> createByParticipant(participant);

        return profile.or(createByParticipant);

    }

    private Profile createByParticipant(Participant participant) {
        Profile profile = new Profile();
        profile.setParticipant(participant);
        profile.setScore(0l);
        profile.setCreatedAt(new Date());
        profile.setUpdatedAt(new Date());
        save(profile);
        return profile;
    }

    public Profile save(Profile profile) {
        return repository.save(profile);
    }

    public Page<Profile> findPage(java.util.Optional<String> organizationId, PageRequest pageRequest) {

        Page<Profile> page = null;

        if (organizationId.isPresent()) {
            page = repository.findAllByParticipantOrganizationBizId(organizationId.get(), pageRequest);
        } else {
            page = repository.findAll(pageRequest);
        }

        return page;
    }

    public Profile findOneById(String id) {
        return repository.findOne(id);
    }
}
