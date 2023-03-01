package com.backend.movie_recommender_backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
public class UserRating {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  @ManyToOne
  private User user;
  @ManyToOne
  private Movie movie;

  private Integer rating;

}
