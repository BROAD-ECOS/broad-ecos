package br.ufjf.nenc.lp.core.repository;

import br.ufjf.nenc.lp.core.entity.Conquest;
import br.ufjf.nenc.lp.core.entity.ConquestType;
import br.ufjf.nenc.lp.core.entity.Profile;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ConquestRepository extends PagingAndSortingRepository<Conquest, String> {


        Conquest findOneByProfileIdAndLearningObjectIdAndInteractionAndType(String id, String learningObjectId, String interaction, ConquestType conquestType);

        List<Conquest> findAllByProfileId(String profileId);
}



