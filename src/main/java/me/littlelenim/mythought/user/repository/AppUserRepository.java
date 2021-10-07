package me.littlelenim.mythought.user.repository;

import me.littlelenim.mythought.user.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    @Query("select a from AppUser a where a.username = ?1")
    Optional<AppUser> findAppUserByUsername(String username);
}
