package ru.nsu.picaptcha.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.nsu.picaptcha.dto.Picture;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class PictureService {

  private final String apiUrl = "http://localhost:5000";

  private RestTemplate restTemplate = new RestTemplate();

  private ObjectMapper objectMapper = new ObjectMapper();

  public boolean process(Picture picture) {
    //TODO (kesha):
    return false;
  }

  public String getRandomWord() {
    String uri = UriComponentsBuilder.fromUriString(apiUrl).path("classes").build().toUriString();
    Map<String, Object> data = getjsonObject(uri);
    List<String> allWords = (ArrayList<String>) data.get("value");

    if (!allWords.isEmpty()) {
      Collections.shuffle(allWords);
      return allWords.get(0);
    } else {
      return null;
    }
  }

  public Boolean verifyPictureClass(Picture picture, String className) {
    ObjectOutput outs = null;
    byte[] bytes = null;

    try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
         ObjectOutput out = new ObjectOutputStream(bos)) {
      out.writeObject(picture);
      out.flush();
      bytes = bos.toByteArray();
    } catch (IOException e) {
      log.warn(e.getMessage());
    }
    String uri = UriComponentsBuilder.fromUriString(apiUrl).path("image_classifier")
            .queryParam("picture", new String(bytes)).build().toUriString();
    Map<String, Object> data = getjsonObject(uri);
    String realClass = (String) data.get("category");

    return realClass.equals(className);
  }

  private Map<String, Object> getjsonObject(String getURI) {
    String result = restTemplate.getForObject(getURI, String.class);
    TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {};
    Map<String, Object> data = null;
    try {
      data = objectMapper.readValue(result, typeRef);
    } catch (IOException e) {
      if (log.isWarnEnabled()) {
        log.warn(e.getMessage());
      }
    }

    return data;
  }
}