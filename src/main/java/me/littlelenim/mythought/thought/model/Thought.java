package me.littlelenim.mythought.thought.model;

import org.hibernate.Hibernate;

import javax.persistence.*;
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
    @ManyToMany
    @JoinTable(
            name = "thought_tag",
            joinColumns = @JoinColumn(name = "thought_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    @OneToMany(mappedBy = "thought", fetch = FetchType.LAZY
            , cascade = CascadeType.ALL)
    private List<Comment> comments;

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
