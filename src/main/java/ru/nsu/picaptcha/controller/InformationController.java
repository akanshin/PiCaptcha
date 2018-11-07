package ru.nsu.picaptcha.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("info")
//TODO: delete this controller
public class InformationController {
    @RequestMapping(value = "/general", method = RequestMethod.GET)
    public ResponseEntity<String> general() {
        return new ResponseEntity<>("general", HttpStatus.OK);
    }

    @RequestMapping(value = "/special", method = RequestMethod.GET)
    public ResponseEntity<String> special() {
        return new ResponseEntity<>("special", HttpStatus.OK);
    }

    @RequestMapping(value = "/secret", method = RequestMethod.GET)
    public ResponseEntity<String> secret() {
        return new ResponseEntity<>("secret", HttpStatus.OK);
    }
}
