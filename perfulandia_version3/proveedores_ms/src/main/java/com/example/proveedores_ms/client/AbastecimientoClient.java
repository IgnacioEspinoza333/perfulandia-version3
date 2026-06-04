package com.example.proveedores_ms.client;

import com.example.proveedores_ms.dto.AbastecimientoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "abastecimiento-ms",
        url = "${abastecimiento.url}"
)
public interface AbastecimientoClient {

    @GetMapping("/abastecimientos/proveedor/{proveedorId}")
    List<AbastecimientoDTO> listarPorProveedor(@PathVariable("proveedorId") Long proveedorId);
}
