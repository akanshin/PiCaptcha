package ru.nsu.picaptcha.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

  @Id
  @GeneratedValue(generator = "increment")
  @GenericGenerator(name= "increment", strategy= "increment")
  @Column
  private long id;
  
  @Column(nullable = false)
  private String login;
  
}
