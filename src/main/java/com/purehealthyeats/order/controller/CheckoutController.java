package com.purehealthyeats.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.purehealthyeats.cart.service.CartService;
import com.purehealthyeats.order.dto.OrderRequestDTO;
import com.purehealthyeats.order.entity.Order;
import com.purehealthyeats.order.service.OrderService;

@Controller
public class CheckoutController {

    @Autowired private CartService cartService;
    @Autowired private OrderService orderService;

    @GetMapping("/checkout")
    public String showCheckoutPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) { return "redirect:/login"; }
        model.addAttribute("cart", cartService.getCartForUser(userDetails.getUsername()));
        model.addAttribute("orderRequest", new OrderRequestDTO());
        return "Billing";
    }

    @PostMapping("/place-order")
    public String placeOrder(@ModelAttribute OrderRequestDTO orderRequest, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) { return "redirect:/login"; }
        Order order = orderService.placeOrder(userDetails.getUsername(), orderRequest);
        // For now, redirecting to a success page (we will build tracking later)
        return "redirect:/order/success/" + order.getId();
    }

    // A temporary success page mapping
    @GetMapping("/order/success/{id}")
    public String orderSuccess(@PathVariable Long id, Model model) {
        model.addAttribute("orderId", id);
        // We will create an OrderSuccess.html page later
        return "redirect:/menu"; // For now, just go back to menu
    }
}
