package ru.nsu.picaptcha.dto;

import lombok.Data;

@Data
public class Picture {
  private byte[] encodedData;
  private String word;
}
