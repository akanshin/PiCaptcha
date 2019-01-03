package ru.nsu.picaptcha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.AllArgsConstructor;
import ru.nsu.picaptcha.dao.UserRepository;

@Service
@AllArgsConstructor
public class UserService {

  @Autowired
  UserRepository userRepository;
  
  public Boolean find(@PathVariable String login) {
    //TODO
    return null;
  }
  
}
