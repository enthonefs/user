package com.cadastro.user.infrastructure.repository;

import com.cadastro.user.infrastructure.entitys.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Transactional
    void deleteByEmail(String email);
}
