package ru.nsu.picaptcha.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column
    private long id;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String password;

    @Column(name = "is_banned", nullable = false)
    private Boolean isBanned = false;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<User> friends = new HashSet<>();

    public boolean addFriend(User user) {
        return friends.add(user);
    }

    public boolean removeFriend(User user) {
        return friends.remove(user);
    }
}
