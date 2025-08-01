package com.purehealthyeats.cart.service;

import com.purehealthyeats.cart.dto.AddToCartRequest;
import com.purehealthyeats.cart.dto.CartDTO;
import com.purehealthyeats.cart.dto.CustomCartRequest;

public interface CartService {
    void addItemToCart(AddToCartRequest request, String username);
    void addCustomItemToCart(CustomCartRequest request, String username);
    CartDTO getCartForUser(String username);
    void updateItemQuantity(Long cartItemId, int quantity, String username);
    void removeItemFromCart(Long cartItemId, String username);
}
