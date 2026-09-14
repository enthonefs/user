package com.cadastro.user.adapters.out.persistence;

import com.cadastro.user.adapters.out.persistence.entities.UsuarioEntity;
import com.cadastro.user.application.ports.out.persistence.UsuarioRepositoryPort;
import com.cadastro.user.domain.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsuarioPersistenceAdapter implements UsuarioRepositoryPort {

    private final UsuarioJpaRepository repository;

    @Override
    public Usuario save(Usuario usuario) {
        UsuarioEntity saved = repository.save(toEntity(usuario));
        return toDomain(saved);
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return repository.findByEmail(email).map(this::toDomain);
    }

    @Override
    public List<Usuario> findAll() {
        return repository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    private UsuarioEntity toEntity(Usuario usuario) {
        return UsuarioEntity.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .build();
    }

    private Usuario toDomain(UsuarioEntity entity) {
        return Usuario.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .email(entity.getEmail())
                .senha(entity.getSenha())
                .build();
    }
}
