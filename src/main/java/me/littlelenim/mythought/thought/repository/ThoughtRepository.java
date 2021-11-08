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

    @Query("select distinct t , c from Thought t left join fetch t.comments c  where t.id = ?1")
    Optional<Thought> findByIdAndJoinCommentsAndLikes(Long id);

    @Query("select distinct t, l from Thought t left join fetch t.likedBy l where t.id =?1")
    Optional<Thought> findByIdAndJoinLikes(Long id);

    @Query("select t from Thought t  order by t.postDate DESC")
    List<Thought> findByOrderByPostDateDesc(Pageable pageable);

    @Query("select t from Thought  t where :tag member t.tags order by t.postDate DESC")
    List<Thought> findByTagOrderByPostDateDesc(Pageable pageable, @Param("tag") Tag tag);

    @Query("select  t from Thought  t where :username = t.author.username order by t.postDate DESC")
    List<Thought> findByUsernameOrderByPostDateDesc(Pageable pageable, @Param("username") String username);

    @Query("select distinct t,u from Thought t left join fetch  t.author u where t=?1 ")
    Thought getThoughtWithAuthor(Thought thought);
}
