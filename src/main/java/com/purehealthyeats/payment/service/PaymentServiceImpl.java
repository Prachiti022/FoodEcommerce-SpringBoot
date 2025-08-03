package com.purehealthyeats.payment.service;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.purehealthyeats.order.entity.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
@Service
public class PaymentServiceImpl implements PaymentService {
    @Value("${razorpay.key.id}")
    private String razorpayKeyId;
    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;
    @Override
    public com.razorpay.Order createRazorpayOrder(Order order) throws RazorpayException {
        RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpayKeySecret);
        JSONObject orderRequest = new JSONObject();
        int amountInPaise = order.getTotalAmount().multiply(new java.math.BigDecimal(100)).intValue();
        orderRequest.put("amount", amountInPaise);
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "order_rcptid_" + order.getId());
        return razorpay.orders.create(orderRequest);
    }
}