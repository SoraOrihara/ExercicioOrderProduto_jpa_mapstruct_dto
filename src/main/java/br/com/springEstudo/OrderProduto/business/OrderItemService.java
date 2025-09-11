package br.com.springEstudo.OrderProduto.business;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.springEstudo.OrderProduto.business.dto.OrderItemRequestDto;
import br.com.springEstudo.OrderProduto.business.dto.OrderItemResponseDto;
import br.com.springEstudo.OrderProduto.business.mapstruct.OrderItemMapper;
import br.com.springEstudo.OrderProduto.exception.ResourceNotFoundException;
import br.com.springEstudo.OrderProduto.insfraestructure.entities.OrderEntity;
import br.com.springEstudo.OrderProduto.insfraestructure.entities.OrderItemEntity;
import br.com.springEstudo.OrderProduto.insfraestructure.entities.ProdutoEntity;
import br.com.springEstudo.OrderProduto.insfraestructure.repositories.OrderItemRepository;
import br.com.springEstudo.OrderProduto.insfraestructure.repositories.OrderRepository;
import br.com.springEstudo.OrderProduto.insfraestructure.repositories.ProdutoRepository;
import jakarta.transaction.Transactional;

@Service
public class OrderItemService {

	private final ProdutoRepository produtoRepository;
	private final OrderItemRepository orderItemRepository;
	private final OrderRepository orderRepository;
	private final OrderItemMapper orderItemMapper;

	public OrderItemService(ProdutoRepository produtoRepository, OrderItemRepository orderItemRepository,
			OrderItemMapper orderItemMapper, OrderRepository orderRepository) {
		this.produtoRepository = produtoRepository;
		this.orderItemRepository = orderItemRepository;
		this.orderItemMapper = orderItemMapper;
		this.orderRepository = orderRepository;
	}

	@Transactional
	public OrderItemEntity create(OrderItemRequestDto itemDto, OrderEntity order) {

		// 1. Usa o mapeador para criar a entidade e busca o produto
		OrderItemEntity item = orderItemMapper.paraOrderItemEntity(itemDto);

		ProdutoEntity produto = produtoRepository.findById(itemDto.produtoId())
				.orElseThrow(() -> new ResourceNotFoundException("Id não encontrado: " + itemDto.produtoId()));

		// 2. Associa a entidade ao pedido e ao produto
		item.setOrder(order);
		item.setProduto(produto);

		// 3. Define o preço de venda e salva
		item.setPriceAtSale(produto.getPrice());

		return orderItemRepository.save(item);
	}

	@Transactional
	public List<OrderItemResponseDto> findAllByOrderId(UUID orderId) {
		OrderEntity order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException(("Id não encontrado: " + orderId)));

		return order.getItens().stream().map(orderItemMapper::paraOrderItemResponseDto).collect(Collectors.toList());

	}

	@Transactional
	public OrderItemResponseDto findById(UUID orderId, UUID itemId) {
		OrderItemEntity orderItem = orderItemRepository.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("Id do item não encontrado: " + itemId));

		if (!orderItem.getOrder().getId().equals(orderId)) {
			throw new IllegalArgumentException("O item não pertece a esta pedido!");
		}

		return orderItemMapper.paraOrderItemResponseDto(orderItem);
	}

	@Transactional
	public OrderItemResponseDto updateOrderItem(UUID orderId, UUID itemId, OrderItemRequestDto request) {
		OrderItemEntity orderItem = orderItemRepository.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException(("Id não encontrado: " + itemId)));

		if (!orderItem.getOrder().getId().equals(orderId)) {
			throw new IllegalArgumentException("O item não pertece a esta pedido!");
		}

		// Atualiza a quantidade do item usando o mapeador
		orderItemMapper.updatePut(request, orderItem);

		// Salva o orderItem

		OrderItemEntity updatedItem = orderItemRepository.save(orderItem);

		// Recalcula o total do pedido principal e salva
		OrderEntity order = updatedItem.getOrder();
		double newTotal = 0.0;
		for (OrderItemEntity item : order.getItens()) {
			newTotal += item.getPriceAtSale() * item.getQuantity();
		}
		order.setTotalAmount(newTotal);
		orderRepository.save(order);

		return orderItemMapper.paraOrderItemResponseDto(updatedItem);
	}

	@Transactional
	public void deleteById(UUID orderId, UUID itemId) {
		OrderItemEntity orderItem = orderItemRepository.findById(itemId)
				.orElseThrow(() -> new IllegalArgumentException("Item de pedido não encontrado."));

		if (!orderItem.getOrder().getId().equals(orderId)) {
			throw new IllegalArgumentException("O item não pertence a este pedido.");
		}

		// Deleta o item do repositório
		orderItemRepository.delete(orderItem);

		// Recalcula o total do pedido principal
		OrderEntity order = orderItem.getOrder();
		double newTotal = 0.0;
		for (OrderItemEntity item : order.getItens()) {
			newTotal += item.getPriceAtSale() * item.getQuantity();
		}
		order.setTotalAmount(newTotal);
		orderRepository.save(order);
	}

}
