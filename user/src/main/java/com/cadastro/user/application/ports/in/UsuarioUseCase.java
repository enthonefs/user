package com.cadastro.user.application.ports.in;

import com.cadastro.user.adapters.in.web.UsuarioDTO;

import java.util.List;

public interface UsuarioUseCase {

    UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO);

    String login(UsuarioDTO dto);

    UsuarioDTO buscarUsuarioPorId(Long id);

    List<UsuarioDTO> listarTodos();

    void deletarUsuarioPorId(Long id);

    UsuarioDTO atualizarUsuario(String token, UsuarioDTO dto);
}
