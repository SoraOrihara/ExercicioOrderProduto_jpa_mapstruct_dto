package br.com.springEstudo.OrderProduto.insfraestructure.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum OrderStatus {
	PENDENTE,
    PROCESSANDO,
    ENVIADO,
    ENTREGUE,
    CANCELADO
}
