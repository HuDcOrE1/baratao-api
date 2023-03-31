package br.com.br.baratao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.br.baratao.modelo.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	List<Cliente> findByNome(String nome);

}
