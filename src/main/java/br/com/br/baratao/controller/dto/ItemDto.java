package br.com.br.baratao.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.br.baratao.modelo.Item;

public class ItemDto {

	private Long id;
	private Long idPeca;
	private int quantidade;
	
	public ItemDto(Item item) {
		this.id = item.getId();
		this.idPeca = item.getIdPeca();
		this.quantidade = item.getQuantidade();
	}

	public Long getId() {
		return id;
	}


	public Long getIdPeca() {
		return idPeca;
	}

	public void setIdPeca(Long idPeca) {
		this.idPeca = idPeca;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public static List<ItemDto> converter(List<Item> item) {
		return item.stream().map(ItemDto::new).collect(Collectors.toList());
	}

}
