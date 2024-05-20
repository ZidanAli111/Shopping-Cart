package com.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.UserDetails;

@Repository
public interface UserRepository extends JpaRepository<UserDetails, Integer> {

	Optional<UserDetails> findByUsername(String username);
	

}