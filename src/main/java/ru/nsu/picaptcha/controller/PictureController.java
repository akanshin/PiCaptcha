package ru.nsu.picaptcha.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
 
import ru.nsu.picaptcha.dto.Picture;
import ru.nsu.picaptcha.service.PictureService;

@RestController
@RequestMapping(value = "picture", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class PictureController {

  private final PictureService pictureService;

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
 