package com.cadastro.user.infrastructure.repository;

import com.cadastro.user.infrastructure.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Transactional
    void deleteByEmail(String email);
}
