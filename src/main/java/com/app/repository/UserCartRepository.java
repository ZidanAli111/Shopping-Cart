package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.UserCartDetails;


@Repository
public interface UserCartRepository extends JpaRepository<UserCartDetails, Integer> {

	
UserCartDetails findByUserIdAndSku( int userId, String sku);
	
	

}
