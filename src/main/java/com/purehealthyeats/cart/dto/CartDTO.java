package com.purehealthyeats.cart.dto;

import java.math.BigDecimal;
import java.util.List;

public class CartDTO {
    private List<CartItemDTO> items;
    private BigDecimal subtotal;
    public List<CartItemDTO> getItems() { return items; }
    public void setItems(List<CartItemDTO> items) { this.items = items; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}
