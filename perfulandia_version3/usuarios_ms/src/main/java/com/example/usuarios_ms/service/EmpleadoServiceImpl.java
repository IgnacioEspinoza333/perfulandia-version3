package com.example.usuarios_ms.service;

import com.example.usuarios_ms.exception.BusinessException;
import com.example.usuarios_ms.exception.NotFoundException;
import com.example.usuarios_ms.model.Empleado;
import com.example.usuarios_ms.repository.EmpleadoRepository;
import com.example.usuarios_ms.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final UsuarioRepository usuarioRepository;

    public EmpleadoServiceImpl(EmpleadoRepository empleadoRepository,
                               UsuarioRepository usuarioRepository) {
        this.empleadoRepository = empleadoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional
    public Empleado crearDesdeUsuario(Long usuarioId) {
        var usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado: " + usuarioId));

        if (empleadoRepository.existsByUsuario_Id(usuarioId)) {
            throw new BusinessException("El usuario ya está registrado como empleado");
        }

        Empleado e = new Empleado();
        e.setUsuario(usuario);
        e.setActivo(true);
        return empleadoRepository.save(e);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Empleado> listar() {
        return empleadoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Empleado obtenerPorId(Long id) {
        return empleadoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Empleado no encontrado: " + id));
    }

    @Override
    @Transactional
    public Empleado activar(Long id) {
        Empleado e = obtenerPorId(id);
        e.setActivo(true);
        return empleadoRepository.save(e);
    }

    @Override
    @Transactional
    public Empleado desactivar(Long id) {
        Empleado e = obtenerPorId(id);
        e.setActivo(false);
        return empleadoRepository.save(e);
    }
}