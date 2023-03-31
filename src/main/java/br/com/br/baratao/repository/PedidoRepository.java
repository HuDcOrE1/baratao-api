package br.com.br.baratao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.br.baratao.modelo.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	Pedido findById(long id);

}
