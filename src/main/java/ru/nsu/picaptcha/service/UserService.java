package ru.nsu.picaptcha.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import ru.nsu.picaptcha.dao.UserRepository;
import ru.nsu.picaptcha.model.User;

import java.util.*;

@Service
@AllArgsConstructor
public class UserService {

  UserRepository userRepository;

  public Optional<User> find(@PathVariable String login) {
    for (User user : userRepository.findAll()) {
      if (user.getLogin().equals(login))
        return Optional.of(user);
    }

    return Optional.empty();
  }

  public Boolean setBanned(@PathVariable String login, Boolean toBan) {
    Optional<User> user = find(login);

    if (user.isPresent()) {
      user.get().setBanned(toBan);
      userRepository.save(user.get());
      return true;
    } else {
      return false;
    }
  }

  public Boolean addFriend(@PathVariable String myLogin, @PathVariable String otherLogin) {
    Optional<User> myUser = find(myLogin);
    Optional<User> otherUser = find(otherLogin);

    if (myUser.isPresent() && otherUser.isPresent()) {
      boolean result = myUser.get().addFriend(otherUser.get());
      if (result)
        userRepository.save(myUser.get());
      return result;
    } else {
      return false;
    }
  }

  public Boolean removeFriend(@PathVariable String myLogin, @PathVariable String otherLogin) {
    Optional<User> myUser = find(myLogin);
    Optional<User> otherUser = find(otherLogin);

    if (myUser.isPresent() && otherUser.isPresent()) {
      boolean result = myUser.get().removeFriend(otherUser.get());

      if (result)
        userRepository.save(myUser.get());
      return result;
    } else {
      return false;
    }
  }

  public User editUser(@RequestBody User newUser) {
    return userRepository.save(newUser);
  }
}
