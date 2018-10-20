package ru.nsu.picaptcha.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import ru.nsu.picaptcha.dto.Picture;
import ru.nsu.picaptcha.service.PictureService;

@RestController
@RequestMapping(value = "/picture", consumes = MediaType.APPLICATION_JSON_VALUE,
produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class PictureController {

  private final PictureService pictureService;

  @RequestMapping
  public boolean send(@RequestBody Picture picture) {
    return pictureService.process(picture);
  }
  
}
