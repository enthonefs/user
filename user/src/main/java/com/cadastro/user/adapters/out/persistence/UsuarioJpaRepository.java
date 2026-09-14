package com.cadastro.user.adapters.out.persistence;

import com.cadastro.user.adapters.out.persistence.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    void deleteById(Long id);
}
