package ru.nsu.picaptcha.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

@Slf4j
@Service
public class PictureService {

  private final String apiUrl = "http://localhost:5000/api/";

  private RestTemplate restTemplate = new RestTemplate();

  private ObjectMapper objectMapper = new ObjectMapper();

  public String process(Picture picture) {
    byte[] aBytes = pictureToByteArray(picture);
    HttpEntity<byte[]> requestEntity = new HttpEntity<>(aBytes);
    RestTemplate restTemplate = new RestTemplate();
    return restTemplate.exchange(apiUrl + "image_classifier", HttpMethod.POST, requestEntity, String.class).getBody();
  }

  private byte[] pictureToByteArray(Picture picture) {
    if (picture == null) {
      return null;
    }

    BufferedImage bufferedImage = new BufferedImage(Picture.width, Picture.height, BufferedImage.TYPE_BYTE_BINARY);
    Graphics g = bufferedImage.createGraphics();

    g.setColor(Color.white);
    g.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());

    g.setColor(Color.black);
    for (List<Integer> point : picture.getImage()) {
      g.drawRect(bufferedImage.getWidth(), bufferedImage.getHeight(), 1, 1);
    }

    return imageToByteArray(bufferedImage);
  }

  private byte[] imageToByteArray(BufferedImage image) {
    if (image == null) {
      return null;
    }

    byte[] aBytes = null;
    try {
      ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
      ImageIO.write(image, "jpg", byteStream);

      byteStream.flush();
      aBytes = byteStream.toByteArray();
      byteStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return aBytes;
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
    String categoryJson = process(picture);
    Map<String, Object> data = getjsonObject(categoryJson);
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
