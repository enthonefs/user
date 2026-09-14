package com.cadastro.user.application.services;

import com.cadastro.user.adapters.in.web.UsuarioDTO;
import com.cadastro.user.adapters.in.web.mappers.UsuarioWebMapper;
import com.cadastro.user.application.ports.in.UsuarioUseCase;
import com.cadastro.user.application.ports.out.persistence.UsuarioRepositoryPort;
import com.cadastro.user.domain.Usuario;
import com.cadastro.user.infrastructure.exception.ConflictException;
import com.cadastro.user.infrastructure.exception.ResourceNotFoundException;
import com.cadastro.user.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UsuarioUseCase {

    private final UsuarioRepositoryPort repository;
    private final UsuarioWebMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO) {
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = mapper.toDomain(usuarioDTO);
        return mapper.toDto(repository.save(usuario));
    }

    public String login(UsuarioDTO dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha()));
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    public void emailExiste(String email) {
        if (repository.existsByEmail(email)) {
            throw new ConflictException("Email já existe! " + email);
        }
    }

    public UsuarioDTO buscarUsuarioPorId(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id indisponível!"));
        return mapper.toDto(usuario);
    }

    public List<UsuarioDTO> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    public void deletarUsuarioPorId(Long id) {
        Usuario usuario = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Usuário não encontrado!"));
        repository.deleteById(id);
    }

    public UsuarioDTO atualizarUsuario(String token, UsuarioDTO dto) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);

        Usuario usuario = repository.findByEmail(email)
                .map(existing -> mapper.merge(dto, existing))
                .orElseThrow(() -> new ResourceNotFoundException("Email de usuário não encontrado! " + email));

        return mapper.toDto(repository.save(usuario));
    }
}
