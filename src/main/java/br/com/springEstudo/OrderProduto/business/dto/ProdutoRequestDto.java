package br.com.springEstudo.OrderProduto.business.dto;

public record ProdutoRequestDto(String nome, String descricao,
		 Integer estoque,
		 Double price,
		 String category) {

}
