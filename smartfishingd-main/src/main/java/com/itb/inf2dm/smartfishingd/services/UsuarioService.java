package com.itb.inf2dm.smartfishingd.services;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.itb.inf2dm.smartfishingd.model.entity.Usuario;
import com.itb.inf2dm.smartfishingd.repository.UsuarioRepository;
@Service

public class UsuarioService {
    @Autowired
private BCryptPasswordEncoder passwordEncoder;

@Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario save(Usuario usuario) {
        usuario.setSenha(criptografarSeNecessario(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public Usuario login(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));

        String senhaSalva = usuario.getSenha();

        if (senhaSalva != null && senhaSalva.startsWith("$2") && passwordEncoder.matches(senha, senhaSalva)) {
            return usuario;
        }

        if (senhaSalva != null && senhaSalva.equals(senha)) {
            usuario.setSenha(passwordEncoder.encode(senha));
            return usuarioRepository.save(usuario);
        }

            throw new RuntimeException("Senha invalida");
    }

    public Usuario update(Long id, Usuario usuario) {
        Usuario usuarioExistente = findById(id);
        usuarioExistente.setNome(usuario.getNome());
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setSenha(criptografarSeNecessario(usuario.getSenha()));
        usuarioExistente.setId(id);
        usuarioExistente.setFoto(usuario.getFoto());
        usuarioExistente.setNivelAcesso(usuario.getNivelAcesso());
        usuarioExistente.setStatusUsuario(usuario.getStatusUsuario());
        usuarioExistente.setDataCadastro(usuario.getDataCadastro());
        return usuarioRepository.save(usuarioExistente);
    }
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Catalogo nao encontrado com o id " + id));
    }
    public void delete(Long id) {
        Usuario usuarioExistente = findById(id);
        usuarioRepository.delete(usuarioExistente);
    }

    private String criptografarSeNecessario(String senha) {
        if (senha == null || senha.startsWith("$2")) {
            return senha;
        }

        return passwordEncoder.encode(senha);
    }
}
