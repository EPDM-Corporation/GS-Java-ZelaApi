package br.com.zela.api.repository;

import br.com.zela.api.domain.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByPontoIdOrderByCriadoEmDesc(Long pontoId);
}