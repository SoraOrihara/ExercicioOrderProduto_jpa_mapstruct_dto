package br.com.springEstudo.OrderProduto.business.dto;

import java.util.List;


import br.com.springEstudo.OrderProduto.insfraestructure.entities.PaymentMethod;
import jakarta.validation.constraints.NotBlank;

public record OrderRequestDto(
		@NotBlank String customer, 
		String shipping_adress,
		String billing_adress, 
		@NotBlank PaymentMethod payment_method,
		List<OrderItemRequestDto> itens) {

}
