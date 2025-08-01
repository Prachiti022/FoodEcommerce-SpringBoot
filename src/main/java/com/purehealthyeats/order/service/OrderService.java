package com.purehealthyeats.order.service;

import com.purehealthyeats.order.dto.OrderRequestDTO;
import com.purehealthyeats.order.entity.Order;

public interface OrderService {
    Order placeOrder(String username, OrderRequestDTO orderRequest);
}
