package com.purehealthyeats.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.purehealthyeats.cart.dto.AddToCartRequest;
import com.purehealthyeats.cart.dto.CustomCartRequest;
import com.purehealthyeats.cart.service.CartService;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/cart")
    public String viewCart(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) { return "redirect:/login"; }
        model.addAttribute("cart", cartService.getCartForUser(userDetails.getUsername()));
        return "Cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@ModelAttribute AddToCartRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) { return "redirect:/login"; }
        cartService.addItemToCart(request, userDetails.getUsername());
        return "redirect:/product/" + request.getProductId() + "?added=true";
    }

    @PostMapping("/cart/add/custom")
    public String addCustomItemToCart(@ModelAttribute CustomCartRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) { return "redirect:/login"; }
        cartService.addCustomItemToCart(request, userDetails.getUsername());
        return "redirect:/cart?added=custom";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam("cartItemId") Long cartItemId, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) { return "redirect:/login"; }
        cartService.removeItemFromCart(cartItemId, userDetails.getUsername());
        return "redirect:/cart";
    }

    @PostMapping("/cart/update")
    public String updateCartQuantity(@RequestParam("cartItemId") Long cartItemId, @RequestParam("quantity") int quantity, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) { return "redirect:/login"; }
        cartService.updateItemQuantity(cartItemId, quantity, userDetails.getUsername());
        return "redirect:/cart";
    }
}
