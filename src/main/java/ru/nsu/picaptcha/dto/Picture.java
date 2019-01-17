package ru.nsu.picaptcha.dto;

import lombok.Data;

@Data
public class Picture {
  private String encodedData;
  private String word;
}
