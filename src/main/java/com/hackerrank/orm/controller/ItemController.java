package com.hackerrank.orm.controller;

import com.hackerrank.orm.enums.ItemStatus;
import com.hackerrank.orm.model.Item;
import com.hackerrank.orm.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/app/item")
public class ItemController {
    @Autowired
    ItemService itemService;

    //1. insert POST
    @PostMapping
    public ResponseEntity<Item> saveItem(@RequestBody Item item){
        Item returnedItem = itemService.saveItem(item);
        if(null != returnedItem){
            return new ResponseEntity<>(returnedItem, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //2. update PUT
    @PutMapping("{itemId}")
    public ResponseEntity<Item> udpateItem(@PathVariable Integer itemId, @RequestBody Item item){
       if(itemService.isItemExists(itemId)){
           item.setItemId(itemId);
           Item returnedItem = itemService.updateItem(item);
           return new ResponseEntity<>(returnedItem, HttpStatus.OK);
       }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //3. delete by itemId DELETE
    @DeleteMapping("{itemId}")
    public ResponseEntity deleteItem(@PathVariable int itemId){
        if(itemService.isItemExists(itemId)){
            itemService.deleteItem(itemId);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //4. delete all DELETE
    @DeleteMapping
    public ResponseEntity deleteAllItems(){
        itemService.deleteAllItems();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //5. get by itemId GET
    @GetMapping("{itemId}")
    public ResponseEntity<Optional<Item>> fetchItemById(@PathVariable Integer itemId){
        Optional<Item> item = itemService.getItemById(itemId);
        if(item.isPresent()){
            return new ResponseEntity<>(item, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    //6. get all GET
    @GetMapping
    public ResponseEntity<List<Item>> getAllItems(@RequestParam Map<String,Object> allParams) {
        List<Item> items = Collections.emptyList();
        if(allParams.containsKey("itemStatus") && allParams.containsKey("itemEnteredByUser")) {
            items = itemService.getItemByParams((ItemStatus) allParams.get("itemStatus"), (String) allParams.get("itemEnteredByUser"));
        } else if(allParams.containsKey("itemStatus") && allParams.containsKey("itemEnteredByUser")){

        } else {
           items = itemService.getAllItems();
        }
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    //7. filters by fields ?itemStatus={status}&itemEnteredByUser={modifiedBy} GET
    /*@GetMapping("/itemStatus/itemEnteredByUser")
    public ResponseEntity<List<Item>> getItemByParams(@RequestParam("itemStatus") ItemStatus itemStatus, @RequestParam("itemEnteredByUser") String itemEnteredByUser){
        List<Item> itemList = itemService.getItemByParams(itemStatus, itemEnteredByUser);
        return new ResponseEntity<>(itemList, HttpStatus.OK);
    }*/
    //8. select all with sorting and pagination ?pageSize={pageSize}&page={page}&sortBy={sortBy} GET
    @GetMapping("{pageSize}/{page}/{sortByField}")
    public void getPaginationAndSorting(){

    }
    }
