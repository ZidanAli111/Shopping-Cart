package com.cts.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.model.UserCartDetails;
import com.app.repository.UserCartRepository;
import com.app.service.UserCartService;
 
@ExtendWith(MockitoExtension.class)
public class UserCartRepositoryTest {
 
    @Mock
    private UserCartRepository userCartRepositoryMock;
 
    @InjectMocks
    private UserCartService userCartService;
 
    @Test
    public void testFindByUserIdAndSku_UserCartExists() {
        // Arrange
        int userId = 1;
        String sku = "testSKU";
        UserCartDetails userCart = new UserCartDetails();
       
        userCart.setSku(sku);
//        when(userCartRepositoryMock.findByUserIdAndSku(userId, sku)).thenReturn(userCart);
// s  // Act
//        UserCartDetails result = userCartService.findUserCartByUserIdAndSku(userId, sku);
// 
//        // Assert
//        assertEquals(null, result);
    }
}