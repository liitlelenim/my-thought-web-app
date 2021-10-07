package me.littlelenim.mythought.user.service;

import lombok.RequiredArgsConstructor;
import me.littlelenim.mythought.user.exception.NoUserWithGivenUsernameException;
import me.littlelenim.mythought.user.model.AppUser;
import me.littlelenim.mythought.user.repository.AppUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;

    @Transactional
    public AppUser save(AppUser user) {
        return appUserRepository.save(user);
    }

    public AppUser findByUsername(String username) {
        return appUserRepository.findAppUserByUsername(username).orElseThrow(
                () -> new NoUserWithGivenUsernameException("Could not find user with given username"));
    }

    @Transactional
    public void updateBio(String username, String bio) {
        findByUsername(username).setBio(bio);
    }
}
