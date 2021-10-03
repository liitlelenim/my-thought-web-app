package me.littlelenim.mythought.thought.repository;

import me.littlelenim.mythought.thought.model.Tag;
import me.littlelenim.mythought.thought.model.Thought;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ThoughtRepository extends JpaRepository<Thought, Long> {

    @Query("SELECT t , c FROM Thought t LEFT JOIN FETCH t.comments c WHERE t.id = :id")
    Optional<Thought> findByIdAndJoinComments(@Param("id") Long id);

    @Query("SELECT t FROM Thought t ORDER BY t.postDate DESC")
    List<Thought> findByOrderByPostDateDesc(Pageable pageable);

    @Query("SELECT t FROM Thought  t WHERE :tag member t.tags ORDER BY t.postDate DESC")
    List<Thought> findByTagOrderByPostDateDesc(Pageable pageable, @Param("tag") Tag tag);
}
