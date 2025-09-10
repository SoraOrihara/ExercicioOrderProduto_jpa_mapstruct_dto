package br.com.springEstudo.OrderProduto.insfraestructure.entities;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.stereotype.Service;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Service
@Table(name="tb_produto")
public class ProdutoEntity extends Auditable implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID id;
	
	private String nome;
	
	private String descricao;
	
	private Double price;
	
	private String category;

	public ProdutoEntity() {
		super();
	}

	public ProdutoEntity(String nome, String descricao, Double price, String category) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.price = price;
		this.category = category;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public UUID getId() {
		return id;
	}
	
	
	
	
}
