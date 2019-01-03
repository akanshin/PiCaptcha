package ru.nsu.picaptcha.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.picaptcha.dao.UserRepository;
import ru.nsu.picaptcha.model.User;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> find(String login) {
        for (User user : userRepository.findAll()) {
            if (user.getUsername().equals(login))
                return Optional.of(user);
        }

        return Optional.empty();
    }

    @Transactional
    public Boolean setBanned(String login, Boolean toBan) {
        Optional<User> user = find(login);

        if (user.isPresent()) {
            user.get().setIsBanned(toBan);
            userRepository.save(user.get());
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public Boolean addFriend(String myLogin, String otherLogin) {
        Optional<User> myUser = find(myLogin);
        Optional<User> otherUser = find(otherLogin);

        if (myUser.isPresent() && otherUser.isPresent()) {
            boolean result = myUser.get().addFriend(otherUser.get());

            if (result) {
                userRepository.save(myUser.get());
            }
            return result;
        } else {
            return false;
        }
    }

    @Transactional
    public Boolean removeFriend(String myLogin, String otherLogin) {
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

    public User editUser(User newUser) {
        return userRepository.save(newUser);
    }
}
