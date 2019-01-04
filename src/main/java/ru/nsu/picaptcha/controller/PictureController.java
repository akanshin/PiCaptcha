package ru.nsu.picaptcha.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

  @RequestMapping(value = "/random_word", method = RequestMethod.GET)
  public ResponseEntity<String> getRandomWord() {
    String result = pictureService.getRandomWord();

    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @RequestMapping(value = "/class/{className}", method = RequestMethod.POST)
  public ResponseEntity<Boolean> verifyPictureClass(@RequestBody Picture picture, @PathVariable String className) {
    Boolean result = pictureService.verifyPictureClass(picture, className);

    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}
