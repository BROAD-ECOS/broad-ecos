package br.ufjf.nenc.lp.core.service;

import br.ufjf.nenc.lp.core.entity.LearningObject;
import br.ufjf.nenc.lp.core.repository.LearningObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;

@Service
public class LearningObjectService {

    @Autowired
    private LearningObjectRepository repository;

    public LearningObject save(LearningObject lo) {
        return repository.save(lo);
    }

    public Optional<LearningObject>  find(String id) {
        return Optional.of(repository.findOne(id));
    }

    public List<LearningObject> findAll() {
        return newArrayList(repository.findAll().iterator());
    }
}
