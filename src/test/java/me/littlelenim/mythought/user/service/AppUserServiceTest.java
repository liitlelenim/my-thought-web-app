package me.littlelenim.mythought.user.service;

import me.littlelenim.mythought.user.exception.NoUserWithGivenUsernameException;
import me.littlelenim.mythought.user.model.AppUser;
import me.littlelenim.mythought.user.repository.AppUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestPropertySource(properties = "secret=secret")
class AppUserServiceTest {
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private AppUserRepository appUserRepository;

    @AfterEach
    void tearDown() {
        appUserRepository.deleteAll();
    }

    @Test
    void testSavingUser() {
        appUserService.save(new AppUser("test", "test123"));
    }

    @Test
    void testNoUserWithGivenUsernameException() {
        assertThrows(NoUserWithGivenUsernameException.class, () -> appUserService.findByUsername("not_existing_username"));

        appUserService.save(new AppUser("test", "test123"));
        appUserService.findByUsername("test");
    }

    @Test
    void testChangingUserBio() {
        AppUser user = appUserService.save(new AppUser("test", "test123"));
        assertEquals("", user.getBio());
        final String testBio = "TEST BIO";
        appUserService.updateBio(user.getUsername(), testBio);
        assertEquals(testBio, appUserService.findByUsername(user.getUsername()).getBio());
    }

}