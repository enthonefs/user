package com.cadastro.user.business;

import com.cadastro.user.infrastructure.entities.Usuario;
import com.cadastro.user.infrastructure.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public void salvarUsuario(Usuario usuario){
        repository.saveAndFlush(usuario);

    }

    public Usuario buscarUsuarioPorId(Integer id){

        return repository.findById(id).orElseThrow(
                () -> new RuntimeException("Id indisponível!")
        );
    }

    public List<Usuario> listarTodos(){
        return repository.findAll();
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
                .id(usuarioEntity.getId())
                .build();

        repository.saveAndFlush(usuarioAtualizado);
    }
}
