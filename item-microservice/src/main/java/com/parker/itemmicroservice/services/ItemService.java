package com.parker.itemmicroservice.services;

import com.parker.itemmicroservice.models.Item;
import com.parker.itemmicroservice.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;


    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItemById(Long id) {
        return itemRepository.findById(id).get();
    }

    public Item insertNewItem(Item item) {
        return itemRepository.save(item);
    }

    public Item updateItem(Item item) {
        Item newItem = itemRepository.findById(item.getItemId()).get();
        newItem.setName(item.getName());
        newItem.setDescription(item.getDescription());
        return itemRepository.save(newItem);
    }

    public void deleteItemById(Long id) {
        itemRepository.deleteById(id);
    }
}
