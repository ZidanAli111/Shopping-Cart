package com.cts.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cts.model.ItemDetails;
import com.cts.model.UserCartDetails;
import com.cts.model.UserDetails;
import com.cts.repository.ItemRepository;
import com.cts.repository.UserCartRepository;
import com.cts.repository.UserRepository;

public class UserCartServiceImplTest {

    @Mock
    private UserCartRepository userCartRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private UserCartServiceImpl userCartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddToCart_UserNotFound() {
        // Arrange
        int userId = 1;
        String sku = "SKU123";
        int itemQuantity = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act and Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userCartService.addToCart(userId, sku, itemQuantity);
        });
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testAddToCart_ItemNotFound() {
        // Arrange
        int userId = 1;
        String sku = "SKU123";
        int itemQuantity = 1;
        UserDetails userDetails = new UserDetails();
        userDetails.setUserId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(userDetails));
        when(itemRepository.findItemBySku(sku)).thenReturn(null);

        // Act and Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userCartService.addToCart(userId, sku, itemQuantity);
        });
        assertEquals("Item not found!!", exception.getMessage());
    }

    @Test
    void testAddToCart_ItemAlreadyInCart() {
        // Arrange
        int userId = 1;
        String sku = "SKU123";
        int itemQuantity = 1;
        UserDetails userDetails = new UserDetails();
        userDetails.setUserId(userId);
        ItemDetails itemDetails = new ItemDetails();
        itemDetails.setSku(sku);
        UserCartDetails existingCartDetails = new UserCartDetails();
        existingCartDetails.setItemQuantity(1);
        when(userRepository.findById(userId)).thenReturn(Optional.of(userDetails));
        when(itemRepository.findItemBySku(sku)).thenReturn(itemDetails);
        when(userCartRepository.findByUserDetailsAndSku(any(), any())).thenReturn(Optional.of(existingCartDetails));

        // Act
        userCartService.addToCart(userId, sku, itemQuantity);

        // Assert
        verify(userCartRepository).save(existingCartDetails);
        assertEquals(2, existingCartDetails.getItemQuantity());
    }

    @Test
    void testAddToCart_Success() {
        // Arrange
        int userId = 1;
        String sku = "SKU123";
        int itemQuantity = 1;
        UserDetails userDetails = new UserDetails();
        userDetails.setUserId(userId);
        ItemDetails itemDetails = new ItemDetails();
        itemDetails.setSku(sku);
        when(userRepository.findById(userId)).thenReturn(Optional.of(userDetails));
        when(itemRepository.findItemBySku(sku)).thenReturn(itemDetails);
        when(userCartRepository.findByUserDetailsAndSku(any(), any())).thenReturn(Optional.empty());

        // Act
        userCartService.addToCart(userId, sku, itemQuantity);

        // Assert
        verify(userCartRepository).save(any());
    }

  

    @Test
    void testModifyItemQuantity_UserNotFound() {
        // Arrange
        int userId = 1;
        String sku = "SKU123";
        UserCartDetails updatedUserCartDetails = new UserCartDetails();
        updatedUserCartDetails.setItemQuantity(2);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act and Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userCartService.modifyItemQuantity(userId, sku, updatedUserCartDetails);
        });
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testModifyItemQuantity_ItemNotFound() {
        // Arrange
        int userId = 1;
        String sku = "SKU123";
        UserCartDetails updatedUserCartDetails = new UserCartDetails();
        updatedUserCartDetails.setItemQuantity(2);
        UserDetails userDetails = new UserDetails();
        userDetails.setUserId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(userDetails));
        when(userCartRepository.findByUserDetailsAndSku(any(), any())).thenReturn(Optional.empty());

        // Act and Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userCartService.modifyItemQuantity(userId, sku, updatedUserCartDetails);
        });
        assertEquals("Item  not found in user's cart", exception.getMessage());
    }

    @Test
    void testModifyItemQuantity_Success() {
        // Arrange
        int userId = 1;
        String sku = "SKU123";
        int updatedQuantity = 2;
        UserCartDetails updatedUserCartDetails = new UserCartDetails();
        updatedUserCartDetails.setItemQuantity(updatedQuantity);
        UserDetails userDetails = new UserDetails();
        userDetails.setUserId(userId);
        UserCartDetails existingCartDetails = new UserCartDetails();
        when(userRepository.findById(userId)).thenReturn(Optional.of(userDetails));
        when(userCartRepository.findByUserDetailsAndSku(any(), any())).thenReturn(Optional.of(existingCartDetails));

        // Act
        userCartService.modifyItemQuantity(userId, sku, updatedUserCartDetails);

        // Assert
        assertEquals(updatedQuantity, existingCartDetails.getItemQuantity());
        verify(userCartRepository).save(existingCartDetails);
    }

   
    
    @Test
    void testGetAllUserCartItems_Success() {
        // Arrange
        List<UserCartDetails> cartItems = new ArrayList<>();
        cartItems.add(new UserCartDetails());
        when(userCartRepository.findAll()).thenReturn(cartItems);

        // Act
        ResponseEntity<List<UserCartDetails>> response = userCartService.getAllUserCartItems();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cartItems, response.getBody());
    }

    @Test
    void testGetAllUserCartItems_Exception() {
        // Arrange
        when(userCartRepository.findAll()).thenThrow(RuntimeException.class);

        // Act
        ResponseEntity<List<UserCartDetails>> response = userCartService.getAllUserCartItems();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }
    
    @Test
    void testGetUserCartItems_Success() {
        // Arrange
        int userId = 1;
        List<UserCartDetails> cartItems = new ArrayList<>();
        when(userCartRepository.findByUserDetailsUserId(userId)).thenReturn(cartItems);

        // Act
        ResponseEntity<List<UserCartDetails>> response = userCartService.getUserCartItems(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cartItems, response.getBody());
    }

    @Test
    void testGetUserCartItems_Exception() {
        // Arrange
        int userId = 1;
        when(userCartRepository.findByUserDetailsUserId(userId)).thenThrow(RuntimeException.class);

        // Act
        ResponseEntity<List<UserCartDetails>> response = userCartService.getUserCartItems(userId);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }
}
