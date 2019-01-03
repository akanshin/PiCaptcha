package ru.nsu.picaptcha.model;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User {

  @Id
  @GeneratedValue(generator = "increment")
  @GenericGenerator(name= "increment", strategy= "increment")
  @Column
  private long id;

  @Column(nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

//  @Column(nullable = false)
//  private Set<Role> roles;
  
}
