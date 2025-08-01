package com.purehealthyeats.order.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.purehealthyeats.cart.entity.Cart;
import com.purehealthyeats.cart.repository.CartRepository;
import com.purehealthyeats.order.dto.OrderRequestDTO;
import com.purehealthyeats.order.entity.Order;
import com.purehealthyeats.order.entity.OrderItem;
import com.purehealthyeats.order.entity.OrderStatus;
import com.purehealthyeats.order.repository.OrderRepository;
import com.purehealthyeats.user.entity.User;
import com.purehealthyeats.user.repository.UserRepository;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired private UserRepository userRepository;
    @Autowired private CartRepository cartRepository;
    @Autowired private OrderRepository orderRepository;

    @Override
    @Transactional
    public Order placeOrder(String username, OrderRequestDTO orderRequest) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartRepository.findByUserId(user.getId()).orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cannot place an order with an empty cart.");
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.CONFIRMED);

        BigDecimal totalAmount = cart.getItems().stream()
            .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(totalAmount);
        
        String fullAddress = String.format("%s, %s, %s, %s, %s",
            orderRequest.getStreetAddress(),
            orderRequest.getAptSuite(),
            orderRequest.getCity(),
            orderRequest.getZipCode(),
            orderRequest.getFullName()
        );
        order.setDeliveryAddress(fullAddress);

        List<OrderItem> orderItems = cart.getItems().stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            orderItem.setCustomizations(cartItem.getCustomizations());
            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);

        // Save the order
        Order savedOrder = orderRepository.save(order);

        // Clear the user's cart
        cart.getItems().clear();
        cartRepository.save(cart);

        return savedOrder;
    }
}
