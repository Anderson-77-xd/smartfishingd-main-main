package com.itb.inf2dm.smartfishingd.repository;

import com.itb.inf2dm.smartfishingd.model.entity.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

}