package ru.nsu.picaptcha.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;
import javax.persistence.*;
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
  private long id;

  @Column(name = "login", nullable = false)
  private String login;

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
