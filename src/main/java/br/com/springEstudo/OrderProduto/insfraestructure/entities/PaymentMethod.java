package br.com.springEstudo.OrderProduto.insfraestructure.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum PaymentMethod {
	 	CREDIT_CARD,
	    BANK_SLIP,
	    PIX,
	    CASH
}
