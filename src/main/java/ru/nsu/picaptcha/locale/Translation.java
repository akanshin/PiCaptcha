package ru.nsu.picaptcha.locale;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Translation {
  private static Translation instance = null;

  public static Translation getInstance() {
    if (instance == null) {
      instance = new Translation();
    }
    return instance;
  }

  private Map<String, String> map = null;

  public Translation() {
    map = new HashMap<String, String>();
    try {
      File file = new File(this.getClass().getResource("locale.csv").getFile());
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);
      String line;
      while((line = br.readLine()) != null) {
        String[] words = line.split("=");
        map.put(words[0], words[1]);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public String translate(String word) {
    String translation = map.get(word);
    return translation != null ? translation : word;
  }
}
