package br.com.br.baratao.controller.form;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.br.baratao.modelo.Peca;
import br.com.br.baratao.repository.PecaRepository;

public class PecaForm {

	@NotNull @NotEmpty @Length(min = 5)
	private String nome;
	
	@NotNull @NotEmpty @Length(min = 3)
	private String localizacao;
	
	@NotNull @NotEmpty
	private String carro_aplicado;
	
	@NotNull @NotEmpty
	private String valor;
	
	@NotNull
	private int quantidade;


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getLocalizacao() {
		return localizacao;
	}


	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}


	public String getCarro_aplicado() {
		return carro_aplicado;
	}


	public void setCarro_aplicado(String carro_aplicado) {
		this.carro_aplicado = carro_aplicado;
	}


	public String getValor() {
		return valor;
	}


	public void setValor(String valor) {
		this.valor = valor;
	}

	public int getQuantidade() {
		return quantidade;
	}


	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}


	public Peca converter() {
		return new Peca(nome, localizacao, carro_aplicado, new BigDecimal(valor), quantidade);
	}
	
	public Peca atualizar(long id, PecaRepository pecaRepository) {
		Peca peca = pecaRepository.getOne(id);
		peca.setNome(nome);
		peca.setCarro_aplicado(carro_aplicado);
		peca.setLocalizacao(localizacao);
		peca.setValor(new BigDecimal(valor));
		peca.setQuantidade(quantidade);
		return peca;
	}

}
