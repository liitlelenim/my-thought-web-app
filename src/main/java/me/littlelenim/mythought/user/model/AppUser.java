package me.littlelenim.mythought.user.model;

import me.littlelenim.mythought.thought.model.Comment;
import me.littlelenim.mythought.thought.model.Thought;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "app_user")
public class AppUser {
    @SequenceGenerator(
            name = "app_user_sequence",
            sequenceName = "app_user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(generator = "app_user_sequence")
    @Id
    private Long id;
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "password")
    private String password; // tego nie chcesz zwracać, spraw, żeby nie było gettera do tego pola
    // hasła nie powinny być nigdy zwracane, co najwyżej mogą być nadpisane nowym hashem

    @OneToMany(mappedBy = "author")
    private List<Thought> thoughts = new ArrayList<>(); // nie trzeba inicjalizować listy w tym miejscu.
    @OneToMany(mappedBy = "author")
    private List<Comment> comments = new ArrayList<>();
    @ManyToMany(mappedBy = "likedBy")
    private List<Thought> likedThoughts;

    //konstruktor, gettery i settery do zastąpienia adnotacjami Lomboka
    public AppUser() {
    }

    public AppUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public List<Thought> getThoughts() {
        return thoughts;
    }

    public void addThought(Thought thought) {
        this.thoughts.add(thought);
        thought.setAuthor(this);
    }

    public void removeThought(Thought thought) {
        this.thoughts.remove(thought);
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setAuthor(this);
    }

    public void removeComment(Comment comment) {
        this.comments.remove(comment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AppUser user = (AppUser) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
