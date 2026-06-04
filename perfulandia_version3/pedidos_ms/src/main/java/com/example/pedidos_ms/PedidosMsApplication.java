package com.example.pedidos_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PedidosMsApplication {
    public static void main(String[] args) {
        SpringApplication.run(PedidosMsApplication.class, args);
    }
}