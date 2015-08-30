package br.ufjf.nenc.thautology.service;

import br.ufjf.nenc.broadecos.BroadEcosApi;
import br.ufjf.nenc.broadecos.Context;
import br.ufjf.nenc.broadecos.model.ParticipantProfile;
import br.ufjf.nenc.thautology.model.Level;
import br.ufjf.nenc.thautology.model.User;
import br.ufjf.nenc.thautology.repository.UserRepository;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.*;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User retriveOrCreateUser(ParticipantProfile profile) {
        checkArgument(profile!=null);

        Optional<User> userFound = userRepository.findByParticipantId(profile.getId());

        Supplier<User> supplyNewUser = ()-> new User(profile.getId(), profile.getPlatform());

        User user = userFound.orElseGet(supplyNewUser);

        user.setParticipantProfile(profile);

        return userRepository.save(user);
    }

    public void completeLevel(User user) {
        checkArgument(user!=null);

        user.setLevel(user.getLevel().next());
        userRepository.save(user);
    }

    public Optional<User> getUser(String userId) {
        Preconditions.checkArgument(userId != null);

        return Optional.ofNullable(userRepository.findOne(userId));
    }

    public Optional<User> getUser(ParticipantProfile participantProfile) {
        return userRepository.findByParticipantId(participantProfile.getId());
    }
}
