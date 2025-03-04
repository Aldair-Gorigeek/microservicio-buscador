package com.tienda.buscador.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Data
@Document(indexName = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double price;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
