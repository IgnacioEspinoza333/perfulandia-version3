package com.example.proveedores_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.proveedores_ms.client")
public class ProveedoresMsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProveedoresMsApplication.class, args);
    }
}
