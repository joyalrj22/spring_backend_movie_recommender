package com.backend.movie_recommender_backend.repositories;

import com.backend.movie_recommender_backend.entities.UserRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRatingRepository extends JpaRepository<UserRating, Integer> {
}
