package com.cts.serviceImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cts.model.ItemDetails;
import com.cts.repository.ItemRepository;

@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {
	@Mock
	private ItemRepository itemRepositoryMock;
	@InjectMocks
	private ItemServiceImpl itemService;

	@BeforeEach
	void setUp() {
	}

	@Test
	public void testGetAllItems_Success() {
		// Arrange
		List<ItemDetails> items = new ArrayList<>();
		items.add(new ItemDetails());
		items.add(new ItemDetails());
		when(itemRepositoryMock.findAll()).thenReturn(items);

		ResponseEntity<List<ItemDetails>> responseEntity = itemService.getAllItems();
		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(items.size(), responseEntity.getBody().size());
	}

	@Test
	public void testGetAllItems_EmptyList() {
		// Arrange
		when(itemRepositoryMock.findAll()).thenReturn(new ArrayList<>());
		// Act
		ResponseEntity<List<ItemDetails>> responseEntity = itemService.getAllItems();
		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertEquals(0, responseEntity.getBody().size());
	}

	@Test
	public void testGetAllItems_Exception() {
		// Arrange
		when(itemRepositoryMock.findAll()).thenThrow(RuntimeException.class);
		// Act
		ResponseEntity<List<ItemDetails>> responseEntity = itemService.getAllItems();
		// Assert
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertEquals(0, responseEntity.getBody().size());
	}

//	@Test
//	public void testGetItemBySku_ItemFound() {
//		// Arrange
//		String sku = "SKU001";
//		ItemDetails item = new ItemDetails();
//		item.setSku(sku);
//		when(itemRepositoryMock.findItemBySku(sku)).thenReturn(Optional.of(item));
//		// Act
//		ItemDetails result = itemService.getItemBySku(sku);
//		// Assert
//		assertNotNull(result);
//		assertEquals(sku, result.getSku());
//	}
//
//	@Test
//	public void testGetItemBySku_ItemNotFound() {
//		
//		String sku = "NonExistingSKU";
//		when(itemRepositoryMock.findItemBySku(sku)).thenReturn(Optional.empty());
//		// Act
//		ItemDetails result = itemService.getItemBySku(sku);
//		// Assert
//		assertEquals(null, result);
//	}
}