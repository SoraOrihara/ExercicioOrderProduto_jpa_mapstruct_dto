package br.com.springEstudo.OrderProduto.business.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import br.com.springEstudo.OrderProduto.insfraestructure.entities.OrderStatus;
import br.com.springEstudo.OrderProduto.insfraestructure.entities.PaymentMethod;

public record OrderResponseDto(
		UUID id,
		String customer,
		Double totalAmount,
		OrderStatus status,
		String shipping_adress,	
		String billing_adress,
		PaymentMethod payment_method,
		List<OrderItemResponseDto> itens,
		LocalDateTime createdAt,
		LocalDateTime updatedAt) {

}
