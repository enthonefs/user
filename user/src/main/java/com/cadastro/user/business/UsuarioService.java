package com.cadastro.user.business;

import com.cadastro.user.business.converter.UsuarioConverter;
import com.cadastro.user.business.dto.UsuarioDTO;
import com.cadastro.user.infrastructure.entities.Usuario;
import com.cadastro.user.infrastructure.exception.ConflictException;
import com.cadastro.user.infrastructure.exception.ResourceNotFoundException;
import com.cadastro.user.infrastructure.repository.UsuarioRepository;
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
public class UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioConverter converter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO){
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));

        Usuario usuario = converter.paraUsuario(usuarioDTO);
        return converter.paraUsuarioDTO(repository.save(usuario));

    }

    public String login(UsuarioDTO dto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha()));
        return "Bearer " + jwtUtil.generateToken(authentication.getName());

    }

    public void emailExiste(String email){
        try {
            boolean existe = verificaEmailExistente(email);

            if (existe){
                throw new ConflictException("Email já existe");
            }

        }catch (ConflictException e){
            throw new ConflictException("Email já existe! " + email);
        }
    }

    public boolean verificaEmailExistente(String email){
        return repository.existsByEmail(email);
    }

    public UsuarioDTO buscarUsuarioPorId(Integer id){
        Usuario usuario = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Id indisponível!")
        );

        return converter.paraUsuarioDTO(usuario);
    }

    public List<UsuarioDTO> listarTodos(){
        List<Usuario> usuarios = repository.findAll();
        return converter.paraListaUsuarioDTO(usuarios);
    }

    public void deletarUsuarioPorEmail(String email){
        repository.deleteByEmail(email);
    }

    public void atualizarUsuarioPorEmail(Integer id, Usuario usuario){
        Usuario usuarioEntity = repository.findById(id).orElseThrow(() ->
                new RuntimeException("Usuario não encontrado"));
        Usuario usuarioAtualizado = Usuario.builder()
                .email(usuario.getEmail() != null ? usuario.getEmail() :
                        usuarioEntity.getEmail())
                .nome(usuario.getNome() != null ? usuario.getNome() :
                        usuarioEntity.getNome())
                .senha(usuario.getSenha() != null ? usuario.getSenha() :
                        usuarioEntity.getSenha())
                .id(usuarioEntity.getId())
                .build();

        repository.saveAndFlush(usuarioAtualizado);
    }
}
