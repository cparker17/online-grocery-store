package com.parker.clientapplication.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItem {
    private Long id;
    private Long itemId;
    private Item item;
    private Integer amount;
    private Long userId;

    public CartItem(Long id, Long userId) {
        this.id = id;
        this.userId = userId;
    }
}
