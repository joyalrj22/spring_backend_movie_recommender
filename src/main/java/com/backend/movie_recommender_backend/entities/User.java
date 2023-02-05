package com.backend.movie_recommender_backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
  @Id
  private Integer id;
  private boolean dataSetUser;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public boolean getDataSetUser() {
    return dataSetUser;
  }

  public void setDataSetUser(boolean dataSetUser) {
    this.dataSetUser = dataSetUser;
  }

}
