package me.littlelenim.mythought.user.service;

import lombok.RequiredArgsConstructor;
import me.littlelenim.mythought.user.exception.NoUserWithGivenUsernameException;
import me.littlelenim.mythought.user.model.AppUser;
import me.littlelenim.mythought.user.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AppUser save(AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return appUserRepository.save(user);
    }

    public AppUser findByUsername(String username) {
        return appUserRepository.findAppUserByUsername(username).orElseThrow(
                () -> new NoUserWithGivenUsernameException("Could not find user with given username"));
    }
    public AppUser findByUsernameWithThoughts(String username){
        return appUserRepository.findAppUserByUsernameJoinThoughts(username).orElseThrow(
                () -> new NoUserWithGivenUsernameException("Could not find user with given username"));
    }

    @Transactional
    public void updateBio(String username, String bio) {
        findByUsername(username).setBio(bio);
    }
}
