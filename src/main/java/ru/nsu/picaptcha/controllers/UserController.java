package ru.nsu.picaptcha.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import ru.nsu.picaptcha.service.UserService;

@RestController
@RequestMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE,
produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class UserController {

  private final UserService userService;

  @RequestMapping(value = "/{login}", method = RequestMethod.GET)
  public ResponseEntity<Boolean> find(@PathVariable String login) {
    Boolean result = userService.find(login);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }
  
}
