package com.example.usuarios_ms.service;

import com.example.usuarios_ms.dto.RolDTO;
import com.example.usuarios_ms.exception.BusinessException;
import com.example.usuarios_ms.exception.NotFoundException;
import com.example.usuarios_ms.model.Permiso;
import com.example.usuarios_ms.model.Rol;
import com.example.usuarios_ms.model.Usuario;
import com.example.usuarios_ms.model.UsuarioRol;
import com.example.usuarios_ms.repository.PermisoRepository;
import com.example.usuarios_ms.repository.RolRepository;
import com.example.usuarios_ms.repository.UsuarioRepository;
import com.example.usuarios_ms.repository.UsuarioRolRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final PermisoRepository permisoRepository;

    public RolServiceImpl(RolRepository rolRepository,
                          UsuarioRepository usuarioRepository,
                          UsuarioRolRepository usuarioRolRepository,
                          PermisoRepository permisoRepository) {
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioRolRepository = usuarioRolRepository;
        this.permisoRepository = permisoRepository;
    }

    @Override
    @Transactional
    public RolDTO crear(RolDTO dto) {
        String nombre = dto.getNombre().trim();

        if (rolRepository.existsByNombreIgnoreCase(nombre)) {
            throw new BusinessException("Ya existe un rol con ese nombre");
        }

        Rol r = new Rol();
        r.setNombre(nombre);
        return mapToDTO(rolRepository.save(r));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RolDTO> listar() {
        return rolRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RolDTO obtenerPorId(Long id) {
        Rol r = rolRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Rol no encontrado: " + id));
        return mapToDTO(r);
    }

    @Override
    @Transactional
    public RolDTO actualizar(Long id, RolDTO dto) {
        Rol r = rolRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Rol no encontrado: " + id));

        String nombreNuevo = dto.getNombre().trim();
        if (!r.getNombre().equalsIgnoreCase(nombreNuevo)
                && rolRepository.existsByNombreIgnoreCase(nombreNuevo)) {
            throw new BusinessException("Ya existe un rol con ese nombre");
        }

        r.setNombre(nombreNuevo);
        return mapToDTO(rolRepository.save(r));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!rolRepository.existsById(id)) {
            throw new NotFoundException("Rol no encontrado: " + id);
        }
        rolRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void asignarRolAUsuario(Long usuarioId, Long rolId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado: " + usuarioId));

        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new NotFoundException("Rol no encontrado: " + rolId));

        if (usuarioRolRepository.existsByUsuario_IdAndRol_Id(usuarioId, rolId)) {
            throw new BusinessException("El usuario ya tiene asignado ese rol");
        }

        UsuarioRol ur = new UsuarioRol();
        ur.setUsuario(usuario);
        ur.setRol(rol);
        usuarioRolRepository.save(ur);
    }

    @Override
    @Transactional
    public void asignarPermisoARol(Long rolId, Long permisoId) {

        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new NotFoundException("Rol no encontrado: " + rolId));

        Permiso permiso = permisoRepository.findById(permisoId)
                .orElseThrow(() -> new NotFoundException("Permiso no encontrado: " + permisoId));

        // Requiere que Rol tenga Set<Permiso> permisos (ManyToMany) inicializado
        rol.getPermisos().add(permiso);
        rolRepository.save(rol);
    }

    private RolDTO mapToDTO(Rol r) {
        return new RolDTO(r.getId(), r.getNombre());
    }
}