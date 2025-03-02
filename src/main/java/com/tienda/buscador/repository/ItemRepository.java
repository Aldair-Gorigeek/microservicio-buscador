package com.tienda.buscador.repository;

import com.tienda.buscador.model.Item;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ItemRepository extends ElasticsearchRepository<Item, Long> {

    // 🔎 Búsqueda por nombre o descripción (ignora mayúsculas/minúsculas)
    List<Item> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);

    // 🔎 Filtrar por categoría exacta
    List<Item> findByCategory(String category);

    // 🔎 Ordenar productos por precio (ascendente)
    List<Item> findByCategoryOrderByPriceAsc(String category);

    // 🔎 Ordenar productos por precio (descendente)
    List<Item> findByCategoryOrderByPriceDesc(String category);
    
    @Query("{\"match_phrase_prefix\": {\"name\": \"?0\"}}")
    List<Item> searchByName(String query);

}
