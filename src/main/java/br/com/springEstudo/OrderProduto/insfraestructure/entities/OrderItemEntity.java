package br.com.springEstudo.OrderProduto.insfraestructure.entities;

import java.util.UUID;

import org.springframework.stereotype.Service;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Service
public class OrderItemEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID id;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="order_id")
	private OrderEntity order;
	
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="produto_id")
	private ProdutoEntity produto;
	
	private Integer quantity;
	
	private Double priceAtSale;

	public OrderItemEntity() {
		super();
	}

	public OrderEntity getOrder() {
		return order;
	}

	public void setOrder(OrderEntity order) {
		this.order = order;
	}

	public ProdutoEntity getProduto() {
		return produto;
	}

	public void setProduto(ProdutoEntity produto) {
		this.produto = produto;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPriceAtSale() {
		return priceAtSale;
	}

	public void setPriceAtSale(Double priceAtSale) {
		this.priceAtSale = priceAtSale;
	}

	public UUID getId() {
		return id;
	}
	
	
}
