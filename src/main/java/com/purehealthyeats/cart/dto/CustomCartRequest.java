package com.purehealthyeats.cart.dto;

import java.util.List;

public class CustomCartRequest {
    private Long productId;
    private String bowlSize;
    private List<String> topping; // Must match the 'name' attribute in the HTML form

    // Getters and Setters
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public String getBowlSize() { return bowlSize; }
    public void setBowlSize(String bowlSize) { this.bowlSize = bowlSize; }
    public List<String> getTopping() { return topping; }
    public void setTopping(List<String> topping) { this.topping = topping; }
}
