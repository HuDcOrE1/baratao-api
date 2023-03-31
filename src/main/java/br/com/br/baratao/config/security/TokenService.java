package br.com.br.baratao.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.br.baratao.modelo.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	@Value("${forum.jwt.expiration}")
	private String expiration; // variavel de tempo configurado no application propriert
	
	@Value("${forum.jwt.secret}")// variavel de secret configurado no application propriert
	private String secret;

	public String gerarToken(Authentication authentication) throws JsonProcessingException {
		Usuario logado = (Usuario) authentication.getPrincipal(); // Pega o usuário logado
		Date hoje = new Date();
		Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration)); // tempo configurado no application propriert
		
		return Jwts.builder()
				.claim("email", logado.getEmail())
				.setIssuer(logado.getNome()) // Quem gerou
				.setSubject(logado.getId().toString()) // passa o usuário logado
				.setIssuedAt(hoje) // data de geração do token
				.setExpiration(dataExpiracao) // data de expiração
				.signWith(SignatureAlgorithm.HS256, secret) // algoritmo para encriptar o token e a senha
				.compact();
	}
	
	public boolean isTokenValido(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token); // Valida o token
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Long getIdUsuario(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody(); // acessa as informações dentro do token
		return Long.parseLong(claims.getSubject()); // retorna o id do usuário
	}

}
