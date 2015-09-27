package br.ufjf.nenc.broadecos.rankr.service;

import br.ufjf.nenc.broadecos.rankr.model.User;
import br.ufjf.nenc.broadecos.rankr.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class RankingService {

    private final UserRepository userRepository;

    @Autowired
    public RankingService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<User> getTop(PageRequest pageRequest){
        return userRepository.findAllByOrderByPointsDesc(pageRequest);
    }

}
