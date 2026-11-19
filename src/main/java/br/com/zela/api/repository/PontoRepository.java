package br.com.zela.api.repository;

import br.com.zela.api.domain.Ponto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PontoRepository extends JpaRepository<Ponto, Long> {
}