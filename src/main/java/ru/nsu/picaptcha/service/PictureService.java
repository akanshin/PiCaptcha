package ru.nsu.picaptcha.service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ru.nsu.picaptcha.dto.Picture;

@Service
@AllArgsConstructor
public class PictureService {

    private final String url = "http://localhost:5000/api/";

    public String process(Picture picture) {
        Resource resource = new FileSystemResource("/home/vikacech/Documents/projects/PiCaptcha_ML/test.jpg");

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("user-file", resource);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(formData, requestHeaders);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(url + "image_classifier", HttpMethod.POST, requestEntity, String.class).getBody();
    }

}
