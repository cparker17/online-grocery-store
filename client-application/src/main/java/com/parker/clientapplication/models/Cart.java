package com.parker.clientapplication.models;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {
    private Long id;
    private Long userId;
    private List<CartItem> items = new ArrayList<>();
}
