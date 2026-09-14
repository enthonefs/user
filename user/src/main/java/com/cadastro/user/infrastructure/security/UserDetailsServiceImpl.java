package com.cadastro.user.infrastructure.security;

import com.cadastro.user.adapters.out.persistence.UsuarioJpaRepository;
import com.cadastro.user.adapters.out.persistence.entities.UsuarioEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioJpaRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsuarioEntity usuarioEntity = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

        return org.springframework.security.core.userdetails.User
                .withUsername(usuarioEntity.getEmail())
                .password(usuarioEntity.getSenha())
                .build();
    }
}
