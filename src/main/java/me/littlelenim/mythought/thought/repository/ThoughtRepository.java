package me.littlelenim.mythought.thought.repository;

import me.littlelenim.mythought.thought.model.Thought;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThoughtRepository extends JpaRepository<Thought, Long> {
}
