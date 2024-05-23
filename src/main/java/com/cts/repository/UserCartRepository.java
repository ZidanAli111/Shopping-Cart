package com.cts.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.model.UserCartDetails;
import com.cts.model.UserDetails;


@Repository
public interface UserCartRepository extends JpaRepository<UserCartDetails, Integer> {

	
Optional<UserCartDetails> findByUserDetailsAndSku(UserDetails userDetails, String sku);
	
	

}
