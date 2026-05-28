package com.itb.inf2dm.smartfishingd.controller;

import com.itb.inf2dm.smartfishingd.model.entity.Comentario;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.itb.inf2dm.smartfishingd.services.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/comentario")
public class ControllerComentario {

    @Autowired
    private ComentarioService comentarioService;

    @GetMapping
    public ResponseEntity<List<Comentario>> findAll() {
        return ResponseEntity.ok(comentarioService.findAll());
    }

    @PostMapping
    public ResponseEntity<Comentario> salvarComentario(@RequestBody Comentario comentario) {
        Comentario novoComentario = comentarioService.save(comentario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoComentario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> listarComentarioPorId(@PathVariable String id) {
        try {
            return ResponseEntity.ok(comentarioService.findById(Long.parseLong(id)));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(
                Map.of(
                    "status", 400,
                    "error", "bad request",
                    "message", "o id não é valido" + id
                )
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(
                Map.of(
                    "status", 404,
                    "error", "Not Found",
                    "message", "Comentario não encontrado com o id: " + id
                )
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarComentario(@PathVariable String id, @RequestBody Comentario comentario) {
        try {
            return ResponseEntity.ok(comentarioService.update(Long.parseLong(id), comentario));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(
                Map.of(
                    "status", 400,
                    "error", "Bad Request",
                    "message", "O id informado não é válido: " + id
                )
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(
                Map.of(
                    "status", 404,
                    "error", "Not Found",
                    "message", "Comentario não encontrado com o id: " + id
                )
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarComentarioPorId(@PathVariable String id) {
        try {
            comentarioService.delete(Long.parseLong(id));
            return ResponseEntity.ok().body(
                Map.of(
                    "status", 200,
                    "message", "Comentario excluído com sucesso!"
                )
            );
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(
                Map.of(
                    "status", 400,
                    "error", "Bad Request",
                    "message", "O id informado não é válido: " + id
                )
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(
                Map.of(
                    "status", 404,
                    "error", "Not Found",
                    "message", "Comentario não encontrado com o id: " + id
                )
            );
        }
    }
}