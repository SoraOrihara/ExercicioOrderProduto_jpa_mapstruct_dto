package br.com.springEstudo.OrderProduto.business.dto;

import java.util.UUID;

public record OrderItemResponseDto(UUID id,
		UUID produtoId,
		String produtoNome,
		Integer quantity,
		Double priceAtSale) {

}
