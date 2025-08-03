package com.purehealthyeats.order.controller;

import com.purehealthyeats.cart.service.CartService;
import com.purehealthyeats.order.dto.OrderRequestDTO;
import com.purehealthyeats.order.entity.Order;
import com.purehealthyeats.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrderController {

    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/checkout")
    public String showCheckoutPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        model.addAttribute("cart", cartService.getCartForUser(userDetails.getUsername()));
        model.addAttribute("orderRequest", new OrderRequestDTO());
        return "Billing"; // This will show the Billing.html page
    }

    @PostMapping("/place-order")
    public String placeOrder(@ModelAttribute OrderRequestDTO orderRequest, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        Order order = orderService.placeOrder(userDetails.getUsername(), orderRequest);
        // Redirect to the payment page with the new order's ID
        return "redirect:/payment/" + order.getId();
    }
}


