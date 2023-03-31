package br.com.br.baratao.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.br.baratao.modelo.Cliente;
import br.com.br.baratao.modelo.Pedido;

public class ClienteDto {

	private Long id;
	private String nome;
	private String email;
	private String cpf;
	private String telefone;
	private String endereco;
	private List<Pedido> pedidos;
	
	public ClienteDto() {
		
	}
	
	public ClienteDto(Cliente cliente) {
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.email = cliente.getEmail();
		this.cpf = cliente.getCpf();
		this.telefone = cliente.getTelefone();
		this.endereco = cliente.getEndereco();
		this.pedidos = cliente.getPedidos();
	}

	public Long getId() {
		return id;
	}

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

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public static List<ClienteDto> converter(List<Cliente> cliente) {
		return cliente.stream().map(ClienteDto::new).collect(Collectors.toList());
	}

}
