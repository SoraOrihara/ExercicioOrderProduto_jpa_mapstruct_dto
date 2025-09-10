package br.com.springEstudo.OrderProduto.insfraestructure.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name ="tb_order")
public class OrderEntity extends Auditable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID id;
	
	@Column(unique = true,nullable = false)
	private String customer;
	
	private Double totalAmount;
	
	@Column(nullable=false)
	private OrderStatus status;
	
	private String shipping_adress;
	
	private String billing_adress;
	
	@Column(nullable=false)
	private PaymentMethod payment_method;
	
	
	@OneToMany(mappedBy = "order",cascade=CascadeType.ALL,orphanRemoval = true)
	private Set<OrderItemEntity> orderItens = new HashSet<>();


	public OrderEntity() {
		super();
	}


	public OrderEntity(String customer, Double totalAmount, OrderStatus status, String shipping_adress,
			String billing_adress, PaymentMethod payment_method, Set<OrderItemEntity> orderItens) {
		super();
		this.customer = customer;
		this.totalAmount = totalAmount;
		this.status = status;
		this.shipping_adress = shipping_adress;
		this.billing_adress = billing_adress;
		this.payment_method = payment_method;
		this.orderItens = orderItens;
	}


	public String getCustomer() {
		return customer;
	}


	public void setCustomer(String customer) {
		this.customer = customer;
	}


	public Double getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}


	public OrderStatus getStatus() {
		return status;
	}


	public void setStatus(OrderStatus status) {
		this.status = status;
	}


	public String getShipping_adress() {
		return shipping_adress;
	}


	public void setShipping_adress(String shipping_adress) {
		this.shipping_adress = shipping_adress;
	}


	public String getBilling_adress() {
		return billing_adress;
	}


	public void setBilling_adress(String billing_adress) {
		this.billing_adress = billing_adress;
	}


	public PaymentMethod getPayment_method() {
		return payment_method;
	}


	public void setPayment_method(PaymentMethod payment_method) {
		this.payment_method = payment_method;
	}


	public Set<OrderItemEntity> getOrderItens() {
		return orderItens;
	}


	public void setOrderItens(Set<OrderItemEntity> orderItens) {
		this.orderItens = orderItens;
	}


	public UUID getId() {
		return id;
	}
	
	
	
	
	
}
