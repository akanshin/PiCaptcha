package ru.nsu.picaptcha.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import ru.nsu.picaptcha.model.User;
import ru.nsu.picaptcha.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping(
        value = "/api/user", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @RequestMapping(value = "/{login}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> find(@PathVariable String login) {
        Boolean result = userService.find(login).isPresent();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{login}/ban", method = RequestMethod.POST)
    public ResponseEntity<Boolean> ban(@PathVariable String login) {
        Boolean result = userService.setBanned(login, true);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/unban/{login}", method = RequestMethod.POST)
    public ResponseEntity<Boolean> unban(@PathVariable String login) {
        Boolean result = userService.setBanned(login, false);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{login}/add_friend/{friend}", method = RequestMethod.POST)
    public ResponseEntity<Boolean> addFriend(@PathVariable String login, @PathVariable String friend) {
        Boolean result = userService.addFriend(login, friend);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{login}/remove_friend/{friend}", method = RequestMethod.POST)
    public ResponseEntity<Boolean> removeFriend(@PathVariable String login, @PathVariable String friend) {
        Boolean result = userService.removeFriend(login, friend);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> edit(@RequestBody User user) {
        Optional<User> user1 = userService.find(user.getLogin());

        if (user1.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            User result = userService.save(user);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

    }
}
