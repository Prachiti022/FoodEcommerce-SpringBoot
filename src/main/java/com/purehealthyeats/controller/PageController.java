package com.purehealthyeats.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/subscribe")
    public String showSubscriptionPage() {
        // This tells Spring to find and return "Subscription.html"
        return "Subscription";
    }

    @GetMapping("/track-order")
    public String showTrackOrderPage() {
        // This tells Spring to find and return "TrackOrder.html"
        return "OrderTracking";
    }
}
