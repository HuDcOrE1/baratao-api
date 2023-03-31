package br.com.br.baratao.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.br.baratao.modelo.Usuario;
import br.com.br.baratao.repository.UsuarioRepository;


public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {
	
	private TokenService tokenService;
	private UsuarioRepository repository;

	public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioRepository repository) {
		this.tokenService = tokenService;
		this.repository = repository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = recuperarToken(request); // Recupera o token do cabeçalho
		boolean valido = tokenService.isTokenValido(token); // Valida o token
		if (valido) { // Se o token for válido
			autenticarCliente(token); // autentica o usuário
		}
		
		filterChain.doFilter(request, response);
	}

	private void autenticarCliente(String token) {
		Long idUsuario = tokenService.getIdUsuario(token); // recupera o id dentro do token
		Usuario usuario = repository.findById(idUsuario).get(); // recupera o usuário no banco
		UsernamePasswordAuthenticationToken authentication = 
				new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()); // cria uma autorização
		SecurityContextHolder.getContext().setAuthentication(authentication); // Considera que o usuário está autenticado
	}

	private String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization"); // Pega o Header
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null; // Token veio vazio
		}
		
		return token.substring(7, token.length()); // Token veio certo
	}

}
