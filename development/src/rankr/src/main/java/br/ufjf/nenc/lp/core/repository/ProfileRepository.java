package br.ufjf.nenc.lp.core.repository;

import br.ufjf.nenc.lp.core.entity.Participant;
import br.ufjf.nenc.lp.core.entity.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProfileRepository extends PagingAndSortingRepository<Profile, String> {

        Page<Profile> findAllByParticipantOrganizationBizId(String bizId, Pageable pageRequest);

        Profile findOneByParticipantEmail(String email);
}



