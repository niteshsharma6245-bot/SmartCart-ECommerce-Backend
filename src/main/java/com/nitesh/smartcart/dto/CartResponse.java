package com.nitesh.smartcart.dto;

import java.util.List;

public class CartResponse {

    private Integer cartId;
    private Integer userId;
    private List<CartItemResponse> items;

    public CartResponse() {
    }

    public CartResponse(Integer cartId,
                        Integer userId,
                        List<CartItemResponse> items) {
        this.cartId = cartId;
        this.userId = userId;
        this.items = items;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<CartItemResponse> getItems() {
        return items;
    }

    public void setItems(List<CartItemResponse> items) {
        this.items = items;
    }
}
