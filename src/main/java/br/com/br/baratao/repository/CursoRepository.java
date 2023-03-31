package br.com.br.baratao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.br.baratao.modelo.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {

	Curso findByNome(String nome);

}
