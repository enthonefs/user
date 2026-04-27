package com.cadastro.user.business.converter;

import com.cadastro.user.business.dto.UsuarioDTO;
import com.cadastro.user.infrastructure.entities.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioConverter {

    public Usuario paraUsuario(UsuarioDTO usuarioDTO){
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .build();
    }

    public List<Usuario> paraListaUsuario(List<UsuarioDTO> dtos){
        return dtos.stream()
                .map(this::paraUsuario)
                .toList();
    }

    public UsuarioDTO paraUsuarioDTO(Usuario usuario){
        return UsuarioDTO.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .build();
    }

    public List<UsuarioDTO> paraListaUsuarioDTO(List<Usuario> usuarios){
        return usuarios.stream()
                .map(this::paraUsuarioDTO)
                .toList();
    }


}
