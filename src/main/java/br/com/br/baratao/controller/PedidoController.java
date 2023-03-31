package br.com.br.baratao.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.br.baratao.controller.dto.PedidoDto;
import br.com.br.baratao.controller.form.PedidoForm;
import br.com.br.baratao.modelo.Item;
import br.com.br.baratao.modelo.Pedido;
import br.com.br.baratao.repository.ClienteRepository;
import br.com.br.baratao.repository.ItemRepository;
import br.com.br.baratao.repository.PedidoRepository;
import br.com.br.baratao.repository.UsuarioRepository;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@GetMapping
	public List<PedidoDto> lista() {
		List<Pedido> pedidos = pedidoRepository.findAll();
		return PedidoDto.converter(pedidos);
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<PedidoDto> cadastrar(@Valid @RequestBody PedidoForm form, UriComponentsBuilder uriBuilder) {
		List<Item> itens = itemRepository.saveAll(form.getItens());
		Pedido pedido = form.converter(clienteRepository, usuarioRepository);
		pedido.setItens(itens);
		pedidoRepository.save(pedido);
		
		URI uri = uriBuilder.path("/pedido/{id}").buildAndExpand(pedido.getId()).toUri();
		return ResponseEntity.created(uri).body(new PedidoDto(pedido));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PedidoDto> detalhar(@PathVariable Long id) {
		Optional<Pedido> pedido = pedidoRepository.findById(id);
		if (pedido.isPresent()) {
			return ResponseEntity.ok(new PedidoDto(pedido.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<PedidoDto> atualizar(@PathVariable Long id, @RequestBody @Valid PedidoForm form) {
		Optional<Pedido> optional = pedidoRepository.findById(id); // O opitional diz que é um item que pode ou não haver
		if (optional.isPresent()) { // Caso não tenha, ele dá o notFound() abaixo
			Pedido pedido = form.atualizar(id, pedidoRepository, clienteRepository, usuarioRepository);
			System.out.println("");
			return ResponseEntity.ok(new PedidoDto(pedido));
		}
		
		return ResponseEntity.notFound().build(); // Caso não exista o id
	}
//	
//	@DeleteMapping("/{id}")
//	@Transactional
//	public ResponseEntity<?> remover(@PathVariable Long id) {
//		Optional<Topico> optional = topicoRepository.findById(id);
//		if (optional.isPresent()) {
//			topicoRepository.deleteById(id);
//			return ResponseEntity.ok().build();
//		}
//		
//		return ResponseEntity.notFound().build();
//	}

}







