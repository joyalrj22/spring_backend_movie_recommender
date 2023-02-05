package com.backend.movie_recommender_backend.repositories;

import com.backend.movie_recommender_backend.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
