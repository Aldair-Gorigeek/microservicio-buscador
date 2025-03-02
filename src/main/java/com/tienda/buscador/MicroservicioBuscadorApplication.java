package com.tienda.buscador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}) // Deshabilita la DB
public class MicroservicioBuscadorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicioBuscadorApplication.class, args);
    }
}
