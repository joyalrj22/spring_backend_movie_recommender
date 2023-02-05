package com.backend.movie_recommender_backend.controllers;

import com.backend.movie_recommender_backend.repositories.MovieRepository;
import com.backend.movie_recommender_backend.entities.Movie;
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

//  @PostMapping(path="/add")
//  public @ResponseBody String addNewMovie (@RequestParam)

}
