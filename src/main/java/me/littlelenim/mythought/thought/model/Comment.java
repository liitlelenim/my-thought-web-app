package me.littlelenim.mythought.thought.model;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "comment")
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

    public Comment(String content) {
        this.content = content;
        this.postDate = new Date();
    }

    public Comment() {

    }

    public Long getId() {
        return id;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
