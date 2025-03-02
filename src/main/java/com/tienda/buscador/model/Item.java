package com.tienda.buscador.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "items")
public class Item {

    @Id
    private Long id;

    @Field(type = FieldType.Search_As_You_Type)
    private String name;

    @Field(type = FieldType.Text, analyzer = "standard", searchAnalyzer = "standard")
    private String description;

    @Field(type = FieldType.Keyword)  // Para búsquedas exactas
    private String category;

    @Field(type = FieldType.Double)  // Permite ordenar productos por precio
    private double price;

    @Field(type = FieldType.Text)  // Almacena la URL de la imagen
    private String image;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
