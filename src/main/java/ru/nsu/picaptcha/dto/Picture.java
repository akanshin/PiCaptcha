package ru.nsu.picaptcha.model;

import java.util.List;

import org.springframework.data.util.Pair;

import lombok.Data;

@Data
public class Picture {
  
  private List<Pair<Integer, Integer>> image;
  private String word;
  
}
