package com.backend.movie_recommender_backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
public class User {
  @Id
  @Column(nullable = false)
  private Integer id;
  @Column(nullable = false)
  private boolean dataSetUser;
}
