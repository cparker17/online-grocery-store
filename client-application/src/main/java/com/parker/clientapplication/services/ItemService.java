package com.parker.clientapplication.services;

import com.parker.clientapplication.models.Item;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ItemService {
    @Autowired
    @LoadBalanced
    RestTemplate restTemplate;

    @Autowired
    ItemService itemService;

    public List<Item> getAllItems() {
        ResponseEntity<Item[]> response = restTemplate.getForEntity("http://ITEM-MICROSERVICE/item", Item[].class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return Arrays.asList(response.getBody());
        } else {
            return null;
        }
    }

    public Item getItemById(Long id) {
        return restTemplate.getForObject("http://ITEM-MICROSERVICE/item/" + id, Item.class);
    }

    public List<Item> newItem(Item item) {
        restTemplate.postForObject("http://ITEM-MICROSERVICE/item/", item, Item.class);
        return getAllItems();
    }

    public List<Item> updateItem(Item item) {
        HttpClient client = HttpClients.createDefault();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
        restTemplate.patchForObject("http://ITEM-MICROSERVICE/item", item, Item.class);
        return getAllItems();
    }

    public List<Item> deleteItem(Long id) {
        restTemplate.delete("http://ITEM-MICROSERVICE/item/" + id);
        return getAllItems();
    }
}
