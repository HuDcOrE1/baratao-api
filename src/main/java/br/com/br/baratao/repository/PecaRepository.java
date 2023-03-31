package br.com.br.baratao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.br.baratao.modelo.Peca;

public interface PecaRepository extends JpaRepository<Peca, Long> {

	Page<Peca> findByNome(String nome, Pageable paginacao);

}
