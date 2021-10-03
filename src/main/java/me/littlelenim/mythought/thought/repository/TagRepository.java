package me.littlelenim.mythought.thought.repository;

import me.littlelenim.mythought.thought.model.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String name);

    @Query("SELECT t FROM Tag t ORDER BY t.thoughts.size DESC")
    List<Tag> getTagsOrderedByThoughtsAmount(Pageable pageable);
}
