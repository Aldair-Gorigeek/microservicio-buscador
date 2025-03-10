package com.tienda.buscador.repository;

import com.tienda.buscador.model.Item;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

}

