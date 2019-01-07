package ru.nsu.picaptcha.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.nsu.picaptcha.dto.Picture;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class PictureService {

    private final String url = "http://localhost:5000/api/";

    public String process(Picture picture) {

        byte[] aBytes = pictureToByteArray(picture);
        HttpEntity<byte[]> requestEntity = new HttpEntity<>(aBytes);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(url + "image_classifier", HttpMethod.POST, requestEntity, String.class).getBody();
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

    public String getClasses() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url + "classes", String.class);
    }
}
