package com.purehealthyeats.payment.service;
import com.purehealthyeats.order.entity.Order;
import com.razorpay.RazorpayException;
public interface PaymentService {
    com.razorpay.Order createRazorpayOrder(Order order) throws RazorpayException;
}