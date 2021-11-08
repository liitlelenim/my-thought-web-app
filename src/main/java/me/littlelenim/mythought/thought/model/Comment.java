package me.littlelenim.mythought.thought.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.littlelenim.mythought.thought.dto.response.GetCommentDto;
import me.littlelenim.mythought.user.model.AppUser;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "comment")
@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter
public class Comment {
    @SequenceGenerator(
            name = "comment_sequence",
            sequenceName = "comment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(generator = "comment_sequence")
    @Id
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private Long id;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "post_date", nullable = false)
    private Date postDate;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "thought_id", nullable = false)
    private Thought thought;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private AppUser author;

    public Comment(String content) {
        this.content = content;
        this.postDate = new Date();
    }

    // @NoArgsConstructor
//    public Comment() {
//
//    }

    // zamiast pisać gettery i settery

//    public Long getId() {
//        return id;
//    }
//
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public Date getPostDate() {
//        return postDate;
//    }
//
//    public void setPostDate(Date postDate) {
//        this.postDate = postDate;
//    }
//
//    public Thought getThought() {
//        return thought;
//    }
//
//    public void setThought(Thought thought) {
//        this.thought = thought;
//    }
//
//    public AppUser getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(AppUser author) {
//        this.author = author;
//    }

    // nie polecam samodzielnego implementowania equals i hashcode na tym etapie wiedzy
    //poczytaj o kontrakcie equals i hashcode, w tym momencie jest złamany
    // zamiaast tego warto użyć @EqualsAndHashCode z lomboka na klasie
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
//        Comment comment = (Comment) o;
//        return Objects.equals(id, comment.id);
//    }

    // to na pewno nie jest dobrze zaimplementowane, hashcode nie może zwracać tej samej wartości za każdym razem
//    @Override
//    public int hashCode() {
//        return 0;
//    }
}
