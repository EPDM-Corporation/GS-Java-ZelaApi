package br.com.zela.api.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "COMENTARIO")
@Getter
@Setter
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "PONTO_ID")
    private Ponto ponto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "USUARIO_ID")
    private Usuario usuario;

    @Column(name = "TEXTO", nullable = false, length = 500)
    private String texto;

    @Column(name = "CRIADO_EM", nullable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();
}