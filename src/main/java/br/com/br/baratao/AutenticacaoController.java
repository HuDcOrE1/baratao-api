package br.com.br.baratao;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.br.baratao.config.security.TokenService;
import br.com.br.baratao.controller.dto.TokenDto;
import br.com.br.baratao.controller.form.LoginForm;

@RestController
@RequestMapping("/auth")
//@Profile(value = {"prod", "test"})
public class AutenticacaoController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;

	@PostMapping
	public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginForm form) throws JsonProcessingException {
		UsernamePasswordAuthenticationToken dadosLogin = form.converter(); // converte o o email e senha para o autentication
		System.out.println(form.getEmail());
		System.out.println(form.getSenha());
		try {
			Authentication authentication = authManager.authenticate(dadosLogin); // Dispara o processo de autenticação
			String token = tokenService.gerarToken(authentication); // gera o token
			System.out.println(token);
//			No front-end usar um cookie ou um session storage
			return ResponseEntity.ok(new TokenDto(token, "Bearer")); // retorna o token e Bearer é o tipo de autenticação
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
}
