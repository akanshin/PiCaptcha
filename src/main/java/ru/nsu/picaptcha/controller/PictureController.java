package ru.nsu.picaptcha.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.picaptcha.dto.Picture;
import ru.nsu.picaptcha.service.PictureService;

@RestController
@RequestMapping(value = "picture", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class PictureController {

    private final PictureService pictureService;

    @PostMapping("send")
    public String send(@RequestBody Picture picture) {
        return pictureService.process(picture);
    }

}
