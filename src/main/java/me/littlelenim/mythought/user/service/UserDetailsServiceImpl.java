package me.littlelenim.mythought.user.service;

import lombok.RequiredArgsConstructor;
import me.littlelenim.mythought.user.model.AppUser;
import me.littlelenim.mythought.user.repository.AppUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findAppUserByUsername(s).orElseThrow(() -> {
            throw new UsernameNotFoundException("Username not found");
        });
        return new User(user.getUsername(), user.getPassword(), Collections.emptyList());
    }
}
