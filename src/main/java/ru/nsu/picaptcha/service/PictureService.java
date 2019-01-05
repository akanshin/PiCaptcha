package ru.nsu.picaptcha.service;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;
import ru.nsu.picaptcha.dto.Picture;

@Service
@AllArgsConstructor
public class PictureService {

    private final String url = "http://localhost:5000/api/";

    public String process(Picture picture) {
        //На будущее
        //byte[] aBytes = pictureToByteArray(picture);
        //HttpEntity<byte[]> requestEntity = new HttpEntity<>(aBytes);
        //RestTemplate restTemplate = new RestTemplate();
        //return restTemplate.exchange(url + "image_classifier", HttpMethod.POST, requestEntity, String.class).getBody();
      
        Resource resource = new FileSystemResource("/home/vikacech/Documents/projects/PiCaptcha_ML/test.jpg");
        
        BufferedImage image = null;
        try {
          image = ImageIO.read(resource.getFile());
        } catch (IOException e) {
          e.printStackTrace();
        }
        
        //HttpHeaders requestHeaders = new HttpHeaders();
        //requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

        //MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        //formData.add("user-file", resource);

        //HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(formData, requestHeaders);

        byte[] aBytes = imageToByteArray(image);
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
      for (Pair<Integer, Integer> point : picture.getImage()) {
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
}
