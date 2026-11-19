package br.com.zela.api.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PONTO")
@Getter
@Setter
public class Ponto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CATEGORIA_ID")
    private Categoria categoria;

    @ManyToOne(optional = false)
    @JoinColumn(name = "USUARIO_ID")
    private Usuario usuario;

    @Column(name = "LATITUDE", nullable = false, precision = 9, scale = 6)
    private BigDecimal latitude;

    @Column(name = "LONGITUDE", nullable = false, precision = 9, scale = 6)
    private BigDecimal longitude;

    @Column(name = "DESCRICAO", nullable = false, length = 500)
    private String descricao;

    // 0 = aberto, 1 = resolvido
    @Column(name = "RESOLVIDO", nullable = false)
    private Integer resolvido = 0;

    @Column(name = "CRIADO_EM", nullable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();
}