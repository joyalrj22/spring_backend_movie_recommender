package com.backend.movie_recommender_backend.controllers;

import com.backend.movie_recommender_backend.init_db_entries.MovieContentInitializer;
import com.backend.movie_recommender_backend.repositories.MovieRepository;
import com.backend.movie_recommender_backend.entities.Movie;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/movies")
public class MovieController {
  @Autowired
  private MovieRepository movieRepository;

  @GetMapping("/all")
  public @ResponseBody Iterable<Movie> getAllMovieData() {
    return movieRepository.findAll();
  }

  @GetMapping(path="/initdb")
  @Transactional
  public void initDB() {
    System.out.println(movieRepository);
    try {
      new MovieContentInitializer().initialize(movieRepository);
      System.out.println("Initialized database");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

//  @PostMapping(path="/add")
//  public @ResponseBody String addNewMovie (@RequestParam)

}
