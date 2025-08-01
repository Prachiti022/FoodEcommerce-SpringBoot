package com.purehealthyeats.cart.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.purehealthyeats.cart.dto.AddToCartRequest;
import com.purehealthyeats.cart.dto.CartDTO;
import com.purehealthyeats.cart.dto.CartItemDTO;
import com.purehealthyeats.cart.dto.CustomCartRequest;
import com.purehealthyeats.cart.entity.Cart;
import com.purehealthyeats.cart.entity.CartItem;
import com.purehealthyeats.cart.repository.CartItemRepository;
import com.purehealthyeats.cart.repository.CartRepository;
import com.purehealthyeats.product.entity.Product;
import com.purehealthyeats.product.repository.ProductRepository;
import com.purehealthyeats.user.entity.User;
import com.purehealthyeats.user.repository.UserRepository;

@Service
public class CartServiceImpl implements CartService {

    @Autowired private UserRepository userRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private CartRepository cartRepository;
    @Autowired private CartItemRepository cartItemRepository;

    @Override
    @Transactional
    public void addItemToCart(AddToCartRequest request, String username) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
        Cart cart = cartRepository.findByUserId(user.getId()).orElseGet(() -> createCartForUser(user));

        for (CartItem item : cart.getItems()) {
            if (item.getProduct().getId().equals(product.getId()) && (item.getCustomizations() == null || item.getCustomizations().isEmpty())) {
                item.setQuantity(item.getQuantity() + request.getQuantity());
                cartRepository.save(cart);
                return;
            }
        }

        CartItem newItem = new CartItem();
        newItem.setCart(cart);
        newItem.setProduct(product);
        newItem.setQuantity(request.getQuantity());
        cart.getItems().add(newItem);
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void addCustomItemToCart(CustomCartRequest request, String username) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
        Cart cart = cartRepository.findByUserId(user.getId()).orElseGet(() -> createCartForUser(user));

        StringBuilder customDesc = new StringBuilder();
        customDesc.append("Size: ").append(request.getBowlSize());
        if (request.getTopping() != null && !request.getTopping().isEmpty()) {
            customDesc.append(", Extras: ").append(String.join(", ", request.getTopping()));
        }

        CartItem newItem = new CartItem();
        newItem.setCart(cart);
        newItem.setProduct(product);
        newItem.setQuantity(1);
        newItem.setCustomizations(customDesc.toString());
        cart.getItems().add(newItem);
        cartRepository.save(cart);
    }

    @Override
    public CartDTO getCartForUser(String username) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartRepository.findByUserId(user.getId()).orElseGet(() -> createCartForUser(user));
        return convertToDTO(cart);
    }

    @Override
    @Transactional
    public void updateItemQuantity(Long cartItemId, int quantity, String username) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new RuntimeException("Cart item not found"));
        if (!cartItem.getCart().getUser().getEmail().equals(username)) {
            throw new SecurityException("User not authorized to update this cart item");
        }
        if (quantity > 0) {
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        } else {
            removeItemFromCart(cartItemId, username);
        }
    }

    @Override
    @Transactional
    public void removeItemFromCart(Long cartItemId, String username) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new RuntimeException("Cart item not found"));
        if (!cartItem.getCart().getUser().getEmail().equals(username)) {
            throw new SecurityException("User not authorized to remove this cart item");
        }
        cartItemRepository.delete(cartItem);
    }

    private Cart createCartForUser(User user) {
        Cart newCart = new Cart();
        newCart.setUser(user);
        return cartRepository.save(newCart);
    }

    private CartDTO convertToDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        List<CartItemDTO> itemDTOs = cart.getItems().stream().map(this::convertItemToDTO).collect(Collectors.toList());
        cartDTO.setItems(itemDTOs);
        BigDecimal subtotal = itemDTOs.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cartDTO.setSubtotal(subtotal);
        return cartDTO;
    }

    private CartItemDTO convertItemToDTO(CartItem cartItem) {
        CartItemDTO dto = new CartItemDTO();
        dto.setCartItemId(cartItem.getId());
        dto.setProductId(cartItem.getProduct().getId());
        dto.setProductName(cartItem.getProduct().getName());
        dto.setQuantity(cartItem.getQuantity());
        dto.setPrice(cartItem.getProduct().getPrice());
        dto.setImageUrl(cartItem.getProduct().getImageUrl());
        dto.setCustomizations(cartItem.getCustomizations());
        return dto;
    }
}
