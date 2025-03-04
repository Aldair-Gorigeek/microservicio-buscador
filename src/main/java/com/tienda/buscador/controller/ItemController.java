package com.tienda.buscador.controller;

import com.tienda.buscador.model.Item;
import com.tienda.buscador.repository.ItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/items")
public class ItemController {

    private static final AtomicLong sequence = new AtomicLong(1L);

    @Autowired
    private ItemRepository repository;



    @GetMapping
    public List<Item> getAllItems() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Item getItemById(@PathVariable Long id) {
        Optional<Item> item = repository.findById(id);
        return item.orElseThrow(() -> new RuntimeException("Item no encontrado"));
    }

    @PostMapping
    public Item createItem(@RequestBody Item item) {
        if (item.getId() == null) {
            item.setId(sequence.getAndIncrement());
        }
        return repository.save(item);
    }

    @PutMapping("/{id}")
    public Item updateItem(@PathVariable Long id, @RequestBody Item item) {
        item.setId(id);
        return repository.save(item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        repository.deleteById(id);
    }



}
