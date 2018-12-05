package ru.nsu.picaptcha.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.picaptcha.service.UserService;

@RestController
@RequestMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class UserController {

  UserService userService;

  @RequestMapping(value = "/{login}", method = RequestMethod.GET)
  public ResponseEntity<Boolean> find(@PathVariable String login) {
    Boolean result = userService.find(login).isPresent();

    return new ResponseEntity<>(result, HttpStatus.OK);
  }

}
