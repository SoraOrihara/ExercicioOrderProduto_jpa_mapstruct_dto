package br.com.springEstudo.OrderProduto.business;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.springEstudo.OrderProduto.business.dto.OrderItemRequestDto;
import br.com.springEstudo.OrderProduto.business.dto.OrderRequestDto;
import br.com.springEstudo.OrderProduto.business.dto.OrderResponseDto;
import br.com.springEstudo.OrderProduto.business.mapstruct.OrderMapper;
import br.com.springEstudo.OrderProduto.exception.ResourceNotFoundException;
import br.com.springEstudo.OrderProduto.insfraestructure.entities.OrderEntity;
import br.com.springEstudo.OrderProduto.insfraestructure.entities.OrderItemEntity;
import br.com.springEstudo.OrderProduto.insfraestructure.entities.OrderStatus;
import br.com.springEstudo.OrderProduto.insfraestructure.repositories.OrderRepository;
import jakarta.transaction.Transactional;

@Service
public class OrderService {

	private final OrderRepository orderRepository;
	private final OrderMapper orderMapper;
	private final OrderItemService orderItemService;

	public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, OrderItemService orderItemService) {
		this.orderRepository = orderRepository;
		this.orderMapper = orderMapper;
		this.orderItemService = orderItemService;
	}

	@Transactional
	public OrderResponseDto createOrder(OrderRequestDto request) {
		OrderEntity order = orderMapper.paraOrderEntity(request);
		order.setStatus(OrderStatus.PENDENTE);

		// Salvar o pedido para obter um id(necessario para a relação)
		order = orderRepository.save(order);

		// Delega a criação de cada item para o OrderItemService
		for (OrderItemRequestDto itemDto : request.itens()) {
			OrderItemEntity itemCriado = orderItemService.create(itemDto, order);
			order.getItens().add(itemCriado);
		}

		// Calcula o valor total com base nos itens criados
		double totalAmount = order.getItens().stream().mapToDouble(item -> item.getPriceAtSale() * item.getQuantity())
				.sum();
		order.setTotalAmount(totalAmount);

		OrderEntity orderSalvo = orderRepository.save(order);
		return orderMapper.paraOrderResponseDto(orderSalvo);
	}
	
	@Transactional
	public List<OrderResponseDto> findAll() {
		return orderMapper.paraListaOrderResponseDto(orderRepository.findAll());
	}
	
	@Transactional
	public OrderResponseDto findById(UUID id) {
		return orderMapper.paraOrderResponseDto(orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Id não encontrado: " + id)));
	}
	
	@Transactional
	public OrderResponseDto update(UUID id, OrderRequestDto request) {
		OrderEntity order = orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Id não encontrado: " + id));
		orderMapper.updateEntityFromDto(request, order);
		OrderEntity orderAtualizado = orderRepository.save(order);
		return orderMapper.paraOrderResponseDto(orderAtualizado);
	}
	
	@Transactional
	public void deleteById(UUID id) {
		if (!orderRepository.existsById(id)) {
			throw new ResourceNotFoundException("Id não encontrado: " + id);
		}
		orderRepository.deleteById(id);
	}

}
