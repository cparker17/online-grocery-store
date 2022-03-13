package com.parker.clientapplication.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private Long itemId;
    private String name;
    private String description;
    private Long userId;

    public Item(Long itemId, Long userId) {
        this.itemId = itemId;
        this.userId = userId;
    }
}
