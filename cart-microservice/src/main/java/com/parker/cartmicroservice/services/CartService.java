package com.parker.cartmicroservice.services;

import com.parker.cartmicroservice.models.Item;
import com.parker.cartmicroservice.models.Cart;
import com.parker.cartmicroservice.models.CartItem;
import com.parker.cartmicroservice.repositories.CartItemRepository;
import com.parker.cartmicroservice.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    public Cart getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = Cart.builder().userId(userId).build();
            cart = cartRepository.save(cart);
        }
        return cart;
    }

    @Transactional
    public Cart addCartItem(Item newItem) {
        Cart cart = getCartByUserId(newItem.getUserId());
        for (CartItem item : cart.getItems()) {
            if (item.getItemId().equals(newItem.getItemId())) {
                item.setAmount(item.getAmount() + 1);
                return cartRepository.save(cart);
            }
        }
        CartItem cartItem = CartItem.builder()
                .itemId(newItem.getItemId())
                .amount(1)
                .build();
        cart.addCartItem(cartItem);
        cartItemRepository.save(cartItem);
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart removeCartItem(Long cartItemId, Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        cart.removeCartItem(cartItemId);
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart updateAmount(Long userId, Long cartItemId, Integer amount) {
        Cart cart = cartRepository.findByUserId(userId);
        cart.getItems().stream().filter(i -> i.getId().compareTo(cartItemId) == 0)
                .findFirst().ifPresent(cartItem -> cartItem.setAmount(cartItem.getAmount() + amount));
        return cart;
    }

    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }
}
