package com.tienda.buscador.controller;

import com.tienda.buscador.model.Item;
import com.tienda.buscador.repository.ItemRepository;
import com.tienda.buscador.repository.ItemService;

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
    
    @Autowired
    private ItemService itemService;


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

    // 🔎 Nueva búsqueda: Buscar productos por nombre o descripción
    @GetMapping("/search")
    public List<Item> searchItems(@RequestParam String query) {
        return repository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query);
    }

    // 🔎 Nueva búsqueda: Filtrar por categoría
    @GetMapping("/category/{category}")
    public List<Item> getByCategory(@PathVariable String category) {
        return repository.findByCategory(category);
    }

    // 🔎 Ordenar productos por precio (ascendente)
    @GetMapping("/category/{category}/sort/asc")
    public List<Item> getByCategorySortedAsc(@PathVariable String category) {
        return repository.findByCategoryOrderByPriceAsc(category);
    }

    // 🔎 Ordenar productos por precio (descendente)
    @GetMapping("/category/{category}/sort/desc")
    public List<Item> getByCategorySortedDesc(@PathVariable String category) {
        return repository.findByCategoryOrderByPriceDesc(category);
    }
    
    @GetMapping("/search-as-you-type")
    public List<Item> searchAsYouType(@RequestParam String query) {
        return repository.searchByName(query);
    }
    
    @GetMapping("/facets")
    public Map<String, Object> getFacets() throws IOException {
        return itemService.getCategoryFacets();
    }


}
