package me.littlelenim.mythought.thought.model;

import me.littlelenim.mythought.user.model.AppUser;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "thought")
public class Thought {
    @SequenceGenerator(
            name = "thought_sequence",
            sequenceName = "thought_sequence",
            allocationSize = 1
    )
    @GeneratedValue(generator = "thought_sequence")
    @Id
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private Long id;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "post_date", nullable = false)
    private Date postDate;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "thought_tag",
            joinColumns = @JoinColumn(name = "thought_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    @OneToMany(mappedBy = "thought", orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private AppUser author;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_thought_like",
            joinColumns = @JoinColumn(name = "thought_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<AppUser> likedBy = new ArrayList<>();

    public Thought(String content) {
        this.content = content;
        this.postDate = new Date();
    }

    public Thought() {

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

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public AppUser getAuthor() {
        return author;
    }

    public void setAuthor(AppUser author) {
        this.author = author;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setThought(this);
    }

    public void removeComment(Comment comment) {
        this.comments.remove(comment);
    }

    public List<AppUser> getLikedBy() {
        return likedBy;
    }

    public int getLikesAmount() {
        return likedBy.size();
    }

    public void addLike(AppUser user) {
        likedBy.add(user);
    }

    public void removeLike(AppUser user) {
        likedBy.remove(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Thought thought = (Thought) o;
        return Objects.equals(id, thought.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
