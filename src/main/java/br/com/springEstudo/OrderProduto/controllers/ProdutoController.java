package br.com.springEstudo.OrderProduto.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.springEstudo.OrderProduto.business.ProdutoService;
import br.com.springEstudo.OrderProduto.business.dto.ProdutoRequestDto;
import br.com.springEstudo.OrderProduto.business.dto.ProdutoResponseDto;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
	@Autowired
	private ProdutoService produtoService;

	// Endpoint para criar um novo produto
	// POST /api/produtos
	@PostMapping
	public ResponseEntity<ProdutoResponseDto> createProduto(@RequestBody ProdutoRequestDto request) {
		ProdutoResponseDto createdProduto = produtoService.createProduto(request);
		return new ResponseEntity<>(createdProduto, HttpStatus.CREATED);
	}

	// Endpoint para listar todos os produtos
	// GET /api/produtos
	@GetMapping
	public ResponseEntity<List<ProdutoResponseDto>> findAll() {
		List<ProdutoResponseDto> produtos = produtoService.findAll();
		return ResponseEntity.ok(produtos);
	}

	// Endpoint para buscar um produto por ID
	// GET /api/produtos/{id}
	@GetMapping("/{id}")
	public ResponseEntity<ProdutoResponseDto> findById(@PathVariable UUID id) {
		ProdutoResponseDto produto = produtoService.findById(id);
		return ResponseEntity.ok(produto);
	}

	// Endpoint para atualizar um produto existente
	// PUT /api/produtos/{id}
	@PutMapping("/{id}")
	public ResponseEntity<ProdutoResponseDto> updateProduto(@PathVariable UUID id,
			@RequestBody ProdutoRequestDto request) {
		ProdutoResponseDto updatedProduto = produtoService.updateProduto(id, request);
		return ResponseEntity.ok(updatedProduto);
	}

	// Endpoint para deletar um produto por ID
	// DELETE /api/produtos/{id}
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
		// O serviço já lida com a exceção se o ID não for encontrado
		produtoService.deleteById(id);
		return ResponseEntity.noContent().build(); // Retorna 204 No Content
	}
}
