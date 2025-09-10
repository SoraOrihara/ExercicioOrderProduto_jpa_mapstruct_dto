package br.com.springEstudo.OrderProduto.business.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProdutoResponseDto(UUID id,
		String nome,
		String descricao,
		Integer estoque,
		Double price,
		String category,
		LocalDateTime createdAt,
		LocalDateTime updatedAt) {

}
