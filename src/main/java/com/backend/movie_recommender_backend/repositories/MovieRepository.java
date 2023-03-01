package com.backend.movie_recommender_backend.repositories;

import com.backend.movie_recommender_backend.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
