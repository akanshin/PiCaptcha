package ru.nsu.picaptcha.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Base64;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.json.JSONObject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import ru.nsu.picaptcha.dto.Picture;

@Slf4j
@Service
public class PictureService  {

  private final String apiUrl = "http://localhost:5000/api/";

  private RestTemplate restTemplate = new RestTemplate();

  private ObjectMapper objectMapper = new ObjectMapper();

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
    byte[] decoded = Base64.getDecoder().decode(picture.getEncodedData());
    HttpEntity<byte[]> requestEntity = new HttpEntity<>(decoded);
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl + "image_classifier", HttpMethod.POST, requestEntity, String.class);
    String categoryJson = responseEntity.getBody();
    System.out.println(categoryJson);
    String realClass = "undef";
    try {

    	final JSONObject obj = new JSONObject(categoryJson);
    	realClass =  obj.getString("category");
    	System.out.println(realClass);
    } catch (Exception e){
    }

    return realClass.equals(className);
  }

  public String getPictureClass(Picture picture) {
    byte[] decoded = Base64.getDecoder().decode(picture.getEncodedData());
    HttpEntity<byte[]> requestEntity = new HttpEntity<>(decoded);
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl + "image_classifier", HttpMethod.POST, requestEntity, String.class);
    String categoryJson = responseEntity.getBody();
    System.out.println(categoryJson);
    String realClass = "undef";
    try {

    	final JSONObject obj = new JSONObject(categoryJson);
    	realClass =  obj.getString("category");
    	System.out.println(realClass);
    } catch (Exception e){
    }

    return realClass;
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
