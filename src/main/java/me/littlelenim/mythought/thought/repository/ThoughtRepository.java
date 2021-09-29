package me.littlelenim.mythought.thought.repository;

import me.littlelenim.mythought.thought.model.Thought;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ThoughtRepository extends JpaRepository<Thought, Long> {
    List<Thought> findByOrderByPostDateDesc(Pageable pageable);
}
