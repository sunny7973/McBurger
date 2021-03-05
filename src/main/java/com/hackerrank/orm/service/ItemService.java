package com.hackerrank.orm.service;

import com.hackerrank.orm.enums.ItemStatus;
import com.hackerrank.orm.model.Item;
import com.hackerrank.orm.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ItemService {
    @Autowired
    public ItemRepository itemRepository;

    public boolean isItemExists(int itemId){
        return itemRepository.existsById(itemId);
    }

    public Item saveItem(Item item) {
        if(itemRepository.existsById(item.getItemId())){
            return null;
        }else{
            return itemRepository.save(item);
        }
    }

    public Item updateItem(Item item) {
        if(itemRepository.existsById(item.getItemId())){
            return itemRepository.save(item);
        }else{
            return null;
        }
    }

    public void deleteItem(int itemId) {
        itemRepository.deleteById(itemId);
    }

    public void deleteAllItems() {
        itemRepository.deleteAll();
    }

    public Optional<Item> getItemById(int itemId){
        return itemRepository.findById(itemId);

    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public List<Item> getItemByParams(ItemStatus itemStatus, String itemEnteredByUser) {
        return itemRepository.findItemsByItemStatusAndItemEnteredByUser(itemStatus, itemEnteredByUser);
    }
}
