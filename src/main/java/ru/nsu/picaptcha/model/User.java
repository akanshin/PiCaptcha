package ru.nsu.picaptcha.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

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

  public String getLogin() {
    return login;
  }

  public long getId() {
    return id;
  }

  public Boolean getBanned() {
    return isBanned;
  }

  public void setBanned(Boolean banned) {
    isBanned = banned;
  }

  public boolean addFriend(User user) {
    return friends.add(user);
  }

  public boolean removeFriend(User user) {
    return friends.remove(user);
  }

  public Set<User> getFriends() {
    return friends;
  }

  @Override
  public int hashCode() {
    return (int) id;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof User)
      return (((User) obj).getId() == id);
    else
      return false;
  }
}
