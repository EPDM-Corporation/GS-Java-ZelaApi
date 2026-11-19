package br.com.zela.api.service;

import br.com.zela.api.domain.Comentario;
import br.com.zela.api.domain.Ponto;
import br.com.zela.api.domain.Usuario;
import br.com.zela.api.dto.ComentarioResponse;
import br.com.zela.api.dto.CriarComentarioRequest;
import br.com.zela.api.exception.RequisicaoInvalidaException;
import br.com.zela.api.repository.ComentarioRepository;
import br.com.zela.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComentarioService {

    private final ComentarioRepository comentarios;
    private final UsuarioRepository usuarios;
    private final PontoService pontos;

    public List<ComentarioResponse> listar(Long pontoId) {
        pontos.achar(pontoId);
        return comentarios.findByPontoIdOrderByCriadoEmDesc(pontoId)
                .stream().map(ComentarioResponse::de).toList();
    }

    public ComentarioResponse criar(Long pontoId, CriarComentarioRequest req) {
        Ponto ponto = pontos.achar(pontoId);
        Usuario usuario = usuarios.findById(req.usuarioId())
                .orElseThrow(() -> new RequisicaoInvalidaException("Usuário inexistente."));

        Comentario c = new Comentario();
        c.setPonto(ponto);
        c.setUsuario(usuario);
        c.setTexto(req.texto().trim());
        return ComentarioResponse.de(comentarios.save(c));
    }
}