package com.backend.movie_recommender_backend.repositories;

import com.backend.movie_recommender_backend.entities.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Integer> {
}
