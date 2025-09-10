package br.com.springEstudo.OrderProduto.business.dto;

import java.util.UUID;

public record OrderItemRequestDto(UUID produtoId,Integer quantity ) {

}
