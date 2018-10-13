package ru.nsu.picaptcha.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import ru.nsu.picaptcha.service.ISessionService;

@RestController
public class SessionRestController {

  @Autowired
  ISessionService sessionService;
  
}
