package br.com.br.baratao.controller.form;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.br.baratao.modelo.Cliente;
import br.com.br.baratao.modelo.Item;
import br.com.br.baratao.modelo.Pedido;
import br.com.br.baratao.modelo.Usuario;
import br.com.br.baratao.repository.ClienteRepository;
import br.com.br.baratao.repository.PedidoRepository;
import br.com.br.baratao.repository.UsuarioRepository;

public class PedidoForm {

	@NotNull @NotEmpty @Length(min = 5)
	private String formaPagamento;
	
	private String observacao;
	
	@NotNull
	private Long cliente_id;
	
	@NotNull
	private Long usuario_id;
	
	@Valid
	private List<Item> itens;

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

	public Long getCliente_id() {
		return cliente_id;
	}

	public void setCliente_id(Long cliente_id) {
		this.cliente_id = cliente_id;
	}

	public List<Item> getItens() {
		return itens;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}
	
	public Long getUsuario_id() {
		return usuario_id;
	}

	public void setUsuario_id(Long usuario_id) {
		this.usuario_id = usuario_id;
	}

	public Pedido converter(ClienteRepository clienteRepository, UsuarioRepository usuarioRepository) { // recebe o repository
		Cliente cliente = clienteRepository.getOne(cliente_id);
		Usuario usuario = usuarioRepository.getOne(usuario_id);
		return new Pedido(formaPagamento, observacao, cliente, usuario, itens);
	}

	public Pedido atualizar(Long id, PedidoRepository pedidoRepository, ClienteRepository clienteRepository, UsuarioRepository usuarioRepository) {
		Cliente cliente = clienteRepository.getOne(cliente_id);
		Usuario usuario = usuarioRepository.getOne(usuario_id);
		Pedido pedido = pedidoRepository.getOne(id);
		
		pedido.getItens().clear();
		for (Item item : this.itens) {
			System.out.println("");
			item.setPedido(pedido);
			pedido.getItens().add(item);
		}
		
		pedido.setFormaPagamento(this.formaPagamento);
		pedido.setObservacao(this.observacao);
		pedido.setCliente(cliente);
		pedido.setUsuario(usuario);
		
		return pedido;
	}


}
