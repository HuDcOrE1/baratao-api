package br.com.br.baratao.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.br.baratao.modelo.Cliente;
import br.com.br.baratao.repository.ClienteRepository;

public class ClienteForm {

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

	public Cliente converter() {
		return new Cliente(nome, email, cpf, telefone, endereco);
	}
	
	public Cliente atualizar(long id, ClienteRepository clienteRepository) {
		Cliente cliente = clienteRepository.getOne(id);
		cliente.setNome(nome);
		cliente.setEmail(email);;
		cliente.setCpf(cpf);;
		cliente.setTelefone(telefone);
		cliente.setEndereco(endereco);
		return cliente;
	}

}
