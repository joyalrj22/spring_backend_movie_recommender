package com.backend.movie_recommender_backend.repositories;

import com.backend.movie_recommender_backend.entities.UserRating;
import org.springframework.data.repository.CrudRepository;

public interface UserRatingRepository extends CrudRepository<UserRating, Integer> {
}
