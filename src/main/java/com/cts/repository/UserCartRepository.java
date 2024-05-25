package com.cts.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.model.UserCartDetails;
import com.cts.model.UserDetails;

@Repository
public interface UserCartRepository extends JpaRepository<UserCartDetails, Integer> {

	  List<UserCartDetails> findByUserDetailsUserId(int userId);

	Optional<UserCartDetails> findByUserDetailsAndSku(UserDetails userDetails, String sku);

}
