package br.com.br.baratao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.br.baratao.modelo.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
	Item findById(long id);

}
