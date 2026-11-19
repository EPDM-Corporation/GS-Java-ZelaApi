package br.com.zela.api.service;

import br.com.zela.api.domain.Usuario;
import br.com.zela.api.dto.CadastroRequest;
import br.com.zela.api.dto.UsuarioResponse;
import br.com.zela.api.exception.ConflitoException;
import br.com.zela.api.exception.NaoAutorizadoException;
import br.com.zela.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarios;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UsuarioResponse cadastrar(CadastroRequest req) {
        String username = req.username().trim();
        if (usuarios.existsByUsername(username)) {
            throw new ConflitoException("Usuário já existe.");
        }
        Usuario u = new Usuario();
        u.setUsername(username);
        u.setSenhaHash(encoder.encode(req.password()));
        u = usuarios.save(u);
        return new UsuarioResponse(u.getId(), u.getUsername());
    }

    public UsuarioResponse login(CadastroRequest req) {
        Usuario u = usuarios.findByUsername(req.username().trim())
                .filter(x -> encoder.matches(req.password(), x.getSenhaHash()))
                .orElseThrow(() -> new NaoAutorizadoException("Usuário ou senha incorretos."));
        return new UsuarioResponse(u.getId(), u.getUsername());
    }
}