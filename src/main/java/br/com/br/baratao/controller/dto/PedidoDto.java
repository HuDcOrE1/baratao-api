package br.com.br.baratao.controller.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.com.br.baratao.modelo.Cliente;
import br.com.br.baratao.modelo.Item;
import br.com.br.baratao.modelo.Pedido;

public class PedidoDto {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String formaPagamento;
	private String observacao;
	private LocalDateTime dataCriacao = LocalDateTime.now();

	private Cliente cliente;

	private List<Item> listaPecas = new ArrayList<>();
	
	
	public PedidoDto() {
	}
	
	public PedidoDto(Pedido pedido) {
		this.id = pedido.getId();
		this.formaPagamento = pedido.getFormaPagamento();
		this.observacao = pedido.getObservacao();
		this.cliente = new Cliente(pedido.getCliente());
		this.listaPecas = pedido.getItens();
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PedidoDto other = (PedidoDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Item> getListaPecas() {
		return listaPecas;
	}

	public void setListaPecas(List<Item> listaPecas) {
		this.listaPecas = listaPecas;
	}

	public static List<PedidoDto> converter(List<Pedido> pedidos) {
		return pedidos.stream().map(PedidoDto::new).collect(Collectors.toList());
	}



}
