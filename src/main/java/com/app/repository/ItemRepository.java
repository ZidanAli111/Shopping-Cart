package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.ItemDetails;

@Repository
public interface ItemRepository extends JpaRepository<ItemDetails, Integer> {

	ItemDetails findItemBySku(String sku);
}
