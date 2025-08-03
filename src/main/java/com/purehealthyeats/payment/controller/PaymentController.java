package com.purehealthyeats.payment.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.purehealthyeats.order.entity.Order;
import com.purehealthyeats.order.repository.OrderRepository;
import com.purehealthyeats.payment.service.PaymentService;
import com.razorpay.RazorpayException;
@Controller
public class PaymentController {
    @Autowired private OrderRepository orderRepository;
    @Autowired private PaymentService paymentService;
    @Value("${razorpay.key.id}")
    private String razorpayKeyId;

    @GetMapping("/payment/{orderId}")
    public String showPaymentPage(@PathVariable Long orderId, Model model) throws RazorpayException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        com.razorpay.Order razorpayOrder = paymentService.createRazorpayOrder(order);
        model.addAttribute("order", order);
        model.addAttribute("razorpayOrderId", razorpayOrder.get("id"));
        model.addAttribute("razorpayKeyId", razorpayKeyId);
        int amountInPaise = order.getTotalAmount().multiply(new java.math.BigDecimal(100)).intValue();
        model.addAttribute("amount", amountInPaise);
        return "Payment";
    }
}