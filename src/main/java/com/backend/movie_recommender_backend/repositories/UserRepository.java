package com.backend.movie_recommender_backend.repositories;

import com.backend.movie_recommender_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {
}
