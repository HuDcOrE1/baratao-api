package br.com.br.baratao.controller.dto;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;

import br.com.br.baratao.modelo.Peca;

public class PecaDto {

	private Long id;
	private String nome;
	private String localizacao;
	private String carro_aplicado;
	private BigDecimal valor;
	private int quantidade;
	
	public PecaDto(Peca peca) {
		this.id = peca.getId();
		this.nome = peca.getNome();
		this.localizacao = peca.getLocalizacao();
		this.carro_aplicado = peca.getCarro_aplicado();
		this.valor = peca.getValor();
		this.quantidade = peca.getQuantidade();
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public String getCarro_aplicado() {
		return carro_aplicado;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public static Page<PecaDto> converter(Page<Peca> peca) {
		return peca.map(PecaDto::new);
	}

}
