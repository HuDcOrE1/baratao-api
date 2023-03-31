package br.com.br.baratao.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.br.baratao.repository.UsuarioRepository;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {
	// Classe responsável por bloquear os acessos á aplicação
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Serve para fazer configurações de autenticação
		// Controle de acessos, login...
		
		/*
		 * Nesse método ele recebe o formulario de login e senha
		 * Encripta a senha e
		 * passa os dados para a service verificar se os dados batem com os 
		 * do banco de dados  
		 * 
		 * */
		auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
		
		/*
		 * Quando eu for salvar novos usuários usar o new BCryptPasswordEncoder().encode("123456")
		 * para salvar a senha encriptada
		 * */
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Serve para configurções de autorizações, perfis de acessos ..
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET, "/topicos").permitAll()
		.antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
		.antMatchers(HttpMethod.GET, "/usuario").permitAll()
		.antMatchers(HttpMethod.GET, "/usuario/*").permitAll()
		.antMatchers(HttpMethod.POST, "/auth").permitAll() // Diz o método que quer liberar
//		.antMatchers(HttpMethod.GET, "/peca").permitAll()
//		.antMatchers(HttpMethod.GET, "/peca/*").permitAll() // Diz o caminho e o método com parâmetro
		.anyRequest().authenticated() // Para qualquer outra requisição o usuário precisa estar autenticado0
//		.and().formLogin() não vamos usar o login pq vamos usar o token
		.and().csrf().disable() // precisa desabilitar isso para funcionar a requisicao
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Diz para não criar uma sessão pq vamos usar o token
		.and().addFilterBefore(
				new AutenticacaoViaTokenFilter( // Faz as validações do token
						tokenService, usuarioRepository),
				UsernamePasswordAuthenticationFilter.class);
	}	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		// Configurações de recursos estáticos (css, js, imagens... Não tem nessa api)
		
		// Liberando os acessos para o swagger
		web.ignoring().antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
	
	}
}
