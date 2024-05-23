package com.cts.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.model.UserCartDetails;
import com.app.repository.UserCartRepository;
import com.app.serviceImpl.UserCartServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserCartRepositoryTest {

	@Mock
	private UserCartRepository userCartRepositoryMock;

	@InjectMocks
	private UserCartServiceImpl userCartServiceimpl;

	@Test
	public void testFindByUserIdAndSku_UserCartExists() {
		// Arrange
		@SuppressWarnings("unused")
		int userId = 1;
		String sku = "testSKU";
		UserCartDetails userCart = new UserCartDetails();
		userCart.setSku(sku);
	}
}