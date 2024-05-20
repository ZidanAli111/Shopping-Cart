package com.cts.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.model.ItemDetails;
import com.app.repository.ItemRepository;
import com.app.serviceImpl.ItemServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ItemRepositoryTest {

	@Mock
	private ItemRepository itemRepositoryMock;

	@InjectMocks
	private ItemServiceImpl itemService;

	@Test
	public void testFindItemBySku_ItemExists() {
		
		// Arrange
		String sku = "testSKU";
		ItemDetails item = new ItemDetails();
		item.setSku(sku);
		when(itemRepositoryMock.findItemBySku(sku)).thenReturn(item);

		// Act
		ItemDetails result = itemService.getItemBySku(sku);

		// Assert
		assertEquals(sku, result.getSku());
	}

	@Test
	public void testFindItemBySku_ItemDoesNotExist() {
		// Arrange
		String sku = "nonExistingSKU";
		when(itemRepositoryMock.findItemBySku(sku)).thenReturn(null);

		// Act
		ItemDetails result = itemService.getItemBySku(sku);

		// Assert
		assertEquals(null, result);
	}
}