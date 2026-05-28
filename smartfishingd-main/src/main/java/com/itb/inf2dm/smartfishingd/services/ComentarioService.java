package com.itb.inf2dm.smartfishingd.services;

import com.itb.inf2dm.smartfishingd.model.entity.Comentario;
import com.itb.inf2dm.smartfishingd.repository.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    public List<Comentario> findAll() {
        return comentarioRepository.findAll();
    }

    public Comentario save(Comentario comentario) {
        return comentarioRepository.save(comentario);
    }

    public Comentario update(Long id, Comentario comentario) {
        Comentario comentarioExistente = findById(id);
        comentarioExistente.setDescricao(comentario.getDescricao());
        comentarioExistente.setPesqueiroId(comentario.getPesqueiroId());
        comentarioExistente.setUsuarioId(comentario.getUsuarioId());
        comentarioExistente.setDataCadastro(comentario.getDataCadastro());
        comentarioExistente.setId(id);
        return comentarioRepository.save(comentarioExistente);
    }

    public Comentario findById(Long id) {
        return comentarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comentario nao encontrado com o id " + id));
    }

    public void delete(Long id) {
        Comentario comentarioExistente = findById(id);
        comentarioRepository.delete(comentarioExistente);
    }
}