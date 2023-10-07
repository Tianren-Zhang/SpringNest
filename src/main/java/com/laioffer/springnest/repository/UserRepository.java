package com.laioffer.springnest.repository;

import com.laioffer.springnest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// By extending JpaRepository, the UserRepository interface gets a variety of database operations without requiring any implementation.
@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
