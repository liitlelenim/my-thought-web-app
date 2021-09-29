package me.littlelenim.mythought.thought.model;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tag")
public class Tag {
    @SequenceGenerator(
            name = "tag_sequence",
            sequenceName = "tag_sequence",
            allocationSize = 1
    )
    @GeneratedValue(generator = "tag_sequence")
    @Id
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;
    @Column(name = "name", unique = true, nullable = false, updatable = false)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Thought> thoughts;
    public Tag(String name) {
        this.name = name;
    }

    public Tag() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
