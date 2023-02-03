package com.backend.movie_recommender_backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Movie {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private String title;
  private String summary;
  private Integer rating;
  private byte[] poster;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }
  public byte[] getPoster() {
    return poster;
  }

  public void setPoster(byte[] poster) {
    this.poster = poster;
  }

  public Integer getRating() {
    return rating;
  }

  public void setRating(Integer rating) {
    this.rating = rating;
  }
}
