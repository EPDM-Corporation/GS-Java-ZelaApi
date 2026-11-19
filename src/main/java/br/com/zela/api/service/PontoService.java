package br.com.zela.api.service;

import br.com.zela.api.domain.Categoria;
import br.com.zela.api.domain.Ponto;
import br.com.zela.api.domain.Usuario;
import br.com.zela.api.dto.CriarPontoRequest;
import br.com.zela.api.dto.PontoResponse;
import br.com.zela.api.exception.NaoEncontradoException;
import br.com.zela.api.exception.RequisicaoInvalidaException;
import br.com.zela.api.repository.CategoriaRepository;
import br.com.zela.api.repository.PontoRepository;
import br.com.zela.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PontoService {

    private final PontoRepository pontos;
    private final CategoriaRepository categorias;
    private final UsuarioRepository usuarios;

    public List<PontoResponse> listar() {
        return pontos.findAll(Sort.by(Sort.Direction.DESC, "criadoEm"))
                .stream().map(PontoResponse::de).toList();
    }

    public PontoResponse buscar(Long id) {
        return PontoResponse.de(achar(id));
    }

    public PontoResponse criar(CriarPontoRequest req) {
        Categoria categoria = categorias.findByCodigo(req.categoria())
                .orElseThrow(() -> new RequisicaoInvalidaException("Categoria inválida."));
        Usuario usuario = usuarios.findById(req.usuarioId())
                .orElseThrow(() -> new RequisicaoInvalidaException("Usuário inexistente."));

        Ponto p = new Ponto();
        p.setCategoria(categoria);
        p.setUsuario(usuario);
        p.setLatitude(req.latitude());
        p.setLongitude(req.longitude());
        p.setDescricao(req.descricao().trim());
        return PontoResponse.de(pontos.save(p));
    }

    public PontoResponse marcarResolvido(Long id) {
        Ponto p = achar(id);
        p.setResolvido(1);
        return PontoResponse.de(pontos.save(p));
    }

    public Ponto achar(Long id) {
        return pontos.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Ponto não encontrado."));
    }
}