package com.cts.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cts.model.UserCartDetails;
import com.cts.service.UserCartService;

public class UserCartControllerTest {

    @Mock
    private UserCartService userCartService;

    @InjectMocks
    private UserCartController userCartController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddToCart_Success() {
        // Arrange
        int userId = 1;
        String sku = "testSKU";
        int itemQuantity = 2;
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Item added to cart successfully");

        // Act
        ResponseEntity<String> responseEntity = userCartController.addToCart(userId, sku, itemQuantity);

        // Assert
        assertEquals(expectedResponse, responseEntity);
        verify(userCartService).addToCart(userId, sku, itemQuantity);
    }

    @Test
    void testGetUserCartItems_Success() {
        // Arrange
        List<UserCartDetails> cartItems = new ArrayList<>();
        when(userCartService.getAllUserCartItems()).thenReturn(ResponseEntity.ok(cartItems));

        // Act
        ResponseEntity<List<UserCartDetails>> responseEntity = userCartController.getUserCartItems();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(cartItems, responseEntity.getBody());
    }

    @Test
    void testModifyUserCartItem_Success() {
        // Arrange
        int userId = 1;
        String sku = "testSKU";
        UserCartDetails userCartDetails = new UserCartDetails();
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Item quantity modified successfully");

        // Act
        ResponseEntity<String> responseEntity = userCartController.modifyUserCartItem(userId, sku, userCartDetails);

        // Assert
        assertEquals(expectedResponse, responseEntity);
        verify(userCartService).modifyItemQuantity(userId, sku, userCartDetails);
    }

    @Test
    void testGetAllUserCartItems_Success() {
        // Arrange
        int userId = 1;
        List<UserCartDetails> cartItems = new ArrayList<>();
        when(userCartService.getUserCartItems(userId)).thenReturn(ResponseEntity.ok(cartItems));

        // Act
        ResponseEntity<List<UserCartDetails>> responseEntity = userCartController.getAllUserCartItems(userId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(cartItems, responseEntity.getBody());
    }
}
