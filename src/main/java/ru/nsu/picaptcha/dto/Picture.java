package ru.nsu.picaptcha.dto;

import java.util.List;

import org.springframework.data.util.Pair;

import lombok.Data;

@Data
public class Picture {
  public static final int width = 255;
  public static final int height = 255;
  
  private List<Pair<Integer, Integer>> image;
  private String word;
  
}
