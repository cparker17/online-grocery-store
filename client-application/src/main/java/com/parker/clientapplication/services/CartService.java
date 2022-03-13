package com.parker.clientapplication.services;

import com.parker.clientapplication.exceptions.NoSuchCartException;
import com.parker.clientapplication.models.Cart;
import com.parker.clientapplication.models.CartItem;
import com.parker.clientapplication.models.Item;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    @LoadBalanced
    RestTemplate restTemplate;

    @Autowired
    ItemService itemService;

    public Cart getCartByUserId(Long userId) throws NoSuchCartException {
        ResponseEntity<Cart> response =
                restTemplate.getForEntity("http://CART-MICROSERVICE/cart/" + userId, Cart.class);
        return populateCartItems(response.getBody());
    }

    public Cart addItemToCart(Item item) throws NoSuchCartException {
        ResponseEntity<Cart> response =
                restTemplate.postForEntity("http://CART-MICROSERVICE/cart/addItem", item, Cart.class);
        return populateCartItems(response.getBody());
    }

    public Cart removeItemFromCart(Long userId, Long cartItemId) throws NoSuchCartException {
        restTemplate.delete("http://CART-MICROSERVICE/cart/" + userId + "/" + cartItemId);
        return populateCartItems(getCartByUserId(userId));
    }

    public Cart updateItemAmount(Long userId, Long cartItemId, Integer amount) throws NoSuchCartException {
        Cart cart = getCartByUserId(userId);

        Optional<CartItem> cartItemOptional = cart.getItems().stream()
                                                  .filter(x -> cartItemId.equals(x.getId()))
                                                  .findFirst();
        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            if (amount == -1 && cartItem.getAmount() == 1) {
                cart = removeItemFromCart(userId, cartItemId);
            }
        }

        if (!cart.getItems().isEmpty()) {
            HttpClient client = HttpClients.createDefault();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
            cart = restTemplate.patchForObject("http://CART-MICROSERVICE/cart/" + userId + "/"
                    + cartItemId, amount, Cart.class);
        }
        return populateCartItems(cart);
    }

    private Cart populateCartItems(Cart cart) throws NoSuchCartException {
        if (cart != null) {
            cart.getItems().forEach(cartItem -> cartItem.setItem(itemService.getItemById(cartItem.getItemId())));
            return cart;
        } else {
            throw new NoSuchCartException("You don't have any items in your cart.");
        }
    }

    public Cart saveCart(Cart cart) {
        return restTemplate.postForEntity("http://CART-MICROSERVICE/cart", cart, Cart.class).getBody();
    }
}