package br.com.br.baratao.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.br.baratao.controller.dto.PecaDto;
import br.com.br.baratao.controller.form.PecaForm;
import br.com.br.baratao.modelo.Peca;
import br.com.br.baratao.repository.PecaRepository;

@RestController
@RequestMapping("/peca")
public class PecaController {
	
	@Autowired
	private PecaRepository pecaRepository;
	
	// localhost:8080/peca?pagina=0&qtd=1
	//	localhost:8080/peca?page=0&size=3&sort=nome,desc 
	//	localhost:8080/peca?page=0&size=3&sort=nome,asc&sort=id,desc Ordena por mais de um campo
	@GetMapping
	public Page<PecaDto> lista(
			@RequestParam(required = false) String nome, //Os parametro é opcional na url
//			@RequestParam int pagina, // Os parametro é obrigatório na url
//			@RequestParam  int qtd,   // Os parametro é obrigatório na url
//			@RequestParam String ordenacao
			@PageableDefault(sort = "nome", direction = Direction.ASC) Pageable paginacao //Ordenação default por nome asc
			// também é possivel controlar a paginação com o page = 0, size = 10 
//			@PageableDefault(sort = "nome", direction = Direction.ASC, page = 0, size = 10 ) Pageable paginacao
			) {
		
//		Pageable paginacao = PageRequest.of(pagina, qtd, Direction.ASC, ordenacao);
		
		if (nome == null) {
//			List<Peca> pecas = pecaRepository.findAll(); Aqui retorna todos do tipo peca
			Page<Peca> pecas = pecaRepository.findAll(paginacao); //aqui ele retorna um Page
			return PecaDto.converter(pecas);
		} else {
			Page<Peca> pecas = pecaRepository.findByNome(nome, paginacao);
			return PecaDto.converter(pecas);
		}
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<PecaDto> cadastrar(@RequestBody @Valid PecaForm form, UriComponentsBuilder uriBuilder) {
		// Sem o uriBuilder a requisição vai dar 200 mesmo que o banco esteja fora
		Peca peca = form.converter();
		pecaRepository.save(peca);
		
		URI uri = uriBuilder.path("/peca/{id}").buildAndExpand(peca.getId()).toUri();
		return ResponseEntity.created(uri).body(new PecaDto(peca));
		// ResponseEntity.created = Vai dar o código de 201 que é de algo criado
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PecaDto> detalhar(@PathVariable Long id) {
		Optional<Peca> peca = pecaRepository.findById(id);
		if (peca.isPresent()) {
			return ResponseEntity.ok(new PecaDto(peca.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<PecaDto> atualizar(@PathVariable Long id, @RequestBody @Valid PecaForm form) {
		Optional<Peca> optional = pecaRepository.findById(id);
		if (optional.isPresent()) {
			Peca peca = form.atualizar(id, pecaRepository);
			return ResponseEntity.ok(new PecaDto(peca));
		}
		
		return ResponseEntity.notFound().build();
	}
	
//	@DeleteMapping("/{id}")
//	@Transactional
//	public ResponseEntity<?> remover(@PathVariable Long id) {
//		Optional<Peca> optional = pecaRepository.findById(id);
//		if (optional.isPresent()) {
//			pecaRepository.deleteById(id);
//			return ResponseEntity.ok().build();
//		}
//		
//		return ResponseEntity.notFound().build();
//	}

}







