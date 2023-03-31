package br.com.br.baratao.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.br.baratao.modelo.Cliente;
import br.com.br.baratao.modelo.Usuario;
import br.com.br.baratao.repository.ClienteRepository;
import br.com.br.baratao.repository.UsuarioRepository;

public class UsuarioForm {

	@NotNull @NotEmpty @Length(min = 5)
	private String nome;
	
	@NotNull @NotEmpty @Length(min = 5)
	private String email;
	
	@NotNull @NotEmpty
	private String cpf;
	
	@NotNull @NotEmpty
	private String telefone;
	
	@NotNull @NotEmpty
	private String endereco;
	
	@NotNull @NotEmpty
	private String senha;


	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getendereco() {
		return endereco;
	}

	public void setendereco(String endereco) {
		this.endereco = endereco;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
//		this.senha = new BCryptPasswordEncoder().encode(senha);
		this.senha = senha;
	}

	public Usuario converter() {
		return new Usuario(nome, email, cpf, telefone, endereco, senha);
	}
	
	public Usuario atualizar(long id, UsuarioRepository usuarioRepository) {
		Usuario usuario = usuarioRepository.getOne(id);
		usuario.setNome(nome);
		usuario.setEmail(email);;
		usuario.setCpf(cpf);;
		usuario.setTelefone(telefone);
		usuario.setEndereco(endereco);
		usuario.setSenha(senha);
		return usuario;
	}

}
