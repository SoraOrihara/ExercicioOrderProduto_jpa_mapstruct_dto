package br.com.springEstudo.OrderProduto.business;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.springEstudo.OrderProduto.business.dto.OrderItemRequestDto;
import br.com.springEstudo.OrderProduto.business.dto.OrderRequestDto;
import br.com.springEstudo.OrderProduto.business.dto.OrderResponseDto;
import br.com.springEstudo.OrderProduto.business.mapstruct.OrderItemMapper;
import br.com.springEstudo.OrderProduto.business.mapstruct.OrderMapper;
import br.com.springEstudo.OrderProduto.exception.ResourceNotFoundException;
import br.com.springEstudo.OrderProduto.insfraestructure.entities.OrderEntity;
import br.com.springEstudo.OrderProduto.insfraestructure.entities.OrderItemEntity;
import br.com.springEstudo.OrderProduto.insfraestructure.entities.OrderStatus;
import br.com.springEstudo.OrderProduto.insfraestructure.entities.ProdutoEntity;
import br.com.springEstudo.OrderProduto.insfraestructure.repositories.OrderRepository;
import br.com.springEstudo.OrderProduto.insfraestructure.repositories.ProdutoRepository;
import jakarta.transaction.Transactional;

@Service
public class OrderService {

	private final OrderRepository orderRepository;
	private final OrderMapper orderMapper;
	private final OrderItemMapper orderItemMapper;
	private final ProdutoRepository produtoRepository;

	public OrderService(OrderRepository orderRepository, OrderMapper orderMapper,
			OrderItemMapper orderItemMapper,ProdutoRepository produtoRepository) {
		this.orderRepository = orderRepository;
		this.orderMapper = orderMapper;
		this.orderItemMapper = orderItemMapper;
		this.produtoRepository=produtoRepository;
	}

	@Transactional
	public OrderResponseDto createOrder(OrderRequestDto request) {
		OrderEntity order = orderMapper.paraOrderEntity(request);
		order.setStatus(OrderStatus.PENDENTE);

		// 1. Pega os IDs de produto diretamente do DTO (já são UUIDs)
        List<UUID> productUuids = request.itens().stream()
                .map(OrderItemRequestDto::produtoId)
                .collect(Collectors.toList());
        
        // 2. Busca todos os produtos de uma vez usando a lista de UUIDs
        List<ProdutoEntity> produtos = produtoRepository.findAllById(productUuids);
        
        // 3. Cria um mapa para busca rápida (chave: UUID do produto, valor: ProdutoEntity)
        Map<UUID, ProdutoEntity> produtosMap = produtos.stream()
                .collect(Collectors.toMap(ProdutoEntity::getId, p -> p));
        
        // 4. Itera sobre os DTOs para criar as entidades OrderItem
        double totalAmount = 0.0;
        List<OrderItemEntity> itens = order.getItens();

        for (OrderItemRequestDto itemDto : request.itens()) {
            
            // Usa o UUID diretamente do DTO
            ProdutoEntity produto = produtosMap.get(itemDto.produtoId());
            
            if (produto == null) {
                throw new IllegalArgumentException("Produto não encontrado com o ID: " + itemDto.produtoId());
            }

            OrderItemEntity orderItem = orderItemMapper.paraOrderItemEntity(itemDto);
            orderItem.setOrder(order);
            orderItem.setProduto(produto);
            orderItem.setPriceAtSale(produto.getPrice());
            orderItem.setQuantity(itemDto.quantity());

            totalAmount += orderItem.getPriceAtSale() * orderItem.getQuantity();
            itens.add(orderItem);
        }	
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
