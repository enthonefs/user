package com.cadastro.user.controller;

import com.cadastro.user.business.UsuarioService;
import com.cadastro.user.business.dto.UsuarioDTO;
import com.cadastro.user.infrastructure.entities.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDTO> salvarUsuario(@RequestBody UsuarioDTO dto){
        return ResponseEntity.ok(usuarioService.salvarUsuario(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsuarioDTO dto){
        return ResponseEntity.ok(usuarioService.login(dto));
    }

    @GetMapping(params = "id")
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorId(@RequestParam Integer id){
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarTodos(){
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @DeleteMapping(params = "email")
    public ResponseEntity<Void> deletarUsuarioPorEmail(@RequestParam String email){
        usuarioService.deletarUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@RequestHeader("Authorization") String token,
                                                        @RequestBody UsuarioDTO dto){
        return ResponseEntity.ok(usuarioService.atualizarUsuario(token, dto));

    }
}
