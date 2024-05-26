package com.cts.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name="item")
public class ItemDetails {


	@Id
	@Column(name="item_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="SKU")
	private String sku;
	
	
	@Column(name="Item_Decription")
	private String itemDescription;
	
	@Column(name="Item_Quantity")
	private int  itemQuantity;
	
	@Column(name="Item_Cost")
	private float itemCost;
	
	@Column(name="MFR_Number")
	private String mfrNo;
	
	@Column(name="Vendor_Number")
	private int vendorNo;

	
	
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public int getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public float getItemCost() {
		return itemCost;
	}

	public void setItemCost(float itemCost) {
		this.itemCost = itemCost;
	}

	public String getMfrNo() {
		return mfrNo;
	}

	public void setMfrNo(String mfrNo) {
		this.mfrNo = mfrNo;
	}

	public int getVendorNo() {
		return vendorNo;
	}

	public void setVendorNo(int vendorNo) {
		this.vendorNo = vendorNo;
	}
	
}
