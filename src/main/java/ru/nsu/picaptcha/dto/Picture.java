package ru.nsu.picaptcha.dto;

import java.util.List;

import lombok.Data;

@Data
public class Picture {
  public static final int width = 255;
  public static final int height = 255;
  
  private List<List<Integer>> image;
  private String word;
  
}
