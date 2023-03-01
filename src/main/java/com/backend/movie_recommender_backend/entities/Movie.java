package com.backend.movie_recommender_backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
public class Movie {

  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  @Column(nullable = false)
  private Integer id;

  @Column(nullable = false)
  private String title;
  @Column(nullable = true)
  private String posterUrl;

  public Movie(final String title, final String posterUrl) {
    this.title = title;
    this.posterUrl = posterUrl;
  }

  public Movie() {

  }
}
