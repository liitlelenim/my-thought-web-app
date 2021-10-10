package me.littlelenim.mythought.user.service;

import lombok.RequiredArgsConstructor;
import me.littlelenim.mythought.user.dto.SignUpUserDto;
import me.littlelenim.mythought.user.exception.InvalidPasswordException;
import me.littlelenim.mythought.user.exception.InvalidUsernameException;
import me.littlelenim.mythought.user.exception.NoUserWithGivenUsernameException;
import me.littlelenim.mythought.user.exception.UsernameAlreadyTakenException;
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

    public AppUser findByUsernameWithThoughts(String username) {
        return appUserRepository.findAppUserByUsernameJoinThoughts(username).orElseThrow(
                () -> new NoUserWithGivenUsernameException("Could not find user with given username"));
    }

    @Transactional
    public AppUser signUpUser(SignUpUserDto dto) {
        String username = dto.getUsername().trim();
        String password = dto.getPassword().trim();

        validateUsername(username);
        validatePassword(password);

        if (appUserRepository.findAppUserByUsername(username).isPresent()) {
            throw new UsernameAlreadyTakenException("Username is already taken");
        }
        return save(new AppUser(username, password));
    }


    @Transactional
    public void updateBio(String username, String bio) {
        findByUsername(username).setBio(bio);
    }

    private void validateUsername(String username) {
        if (username.contains(" ")) {
            throw new InvalidUsernameException("Username must not contain any white spaces");
        }
    }

    private void validatePassword(String password) {
        boolean containsLowerCase = false;
        boolean containsUpperCase = false;
        boolean containsDigit = false;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                containsDigit = true;
            }
            if (Character.isLowerCase(c)) {
                containsLowerCase = true;
            }
            if (Character.isUpperCase(c)) {
                containsUpperCase = true;
            }
        }
        if (!containsLowerCase) {
            throw new InvalidPasswordException("Password must contain at least one lower case letter");
        }
        if (!containsUpperCase) {
            throw new InvalidPasswordException("Password must contain at least one upper case letter");
        }
        if (!containsDigit) {
            throw new InvalidPasswordException("Password must contain at least one digit");
        }
    }

}
