package br.com.springEstudo.OrderProduto.business.mapstruct;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import br.com.springEstudo.OrderProduto.business.dto.OrderItemRequestDto;
import br.com.springEstudo.OrderProduto.business.dto.OrderItemResponseDto;
import br.com.springEstudo.OrderProduto.insfraestructure.entities.OrderItemEntity;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

	
	@Mapping(target = "id", ignore = true) // O ID é gerado pelo servidor
    @Mapping(target = "order", ignore = true) // A entidade Order é setada pelo serviço
    @Mapping(target = "produto", ignore = true) // A entidade Produto é setada pelo serviço
    @Mapping(target = "priceAtSale", ignore = true) // O preço é calculado pelo serviço
	OrderItemEntity paraOrderItemEntity(OrderItemRequestDto request);
	
	// Método para mapear a entidade OrderItem para o DTO de resposta
    @Mapping(source = "produto.id", target = "produtoId")
    @Mapping(source = "produto.nome", target = "produtoNome")
	OrderItemResponseDto paraOrderItemResponseDto(OrderItemEntity entity);
	
    @Mapping(source = "produto.id", target = "produtoId")
    @Mapping(source = "produto.nome", target = "produtoNome")
	List<OrderItemResponseDto> paraListOrderItemResponseDto(List<OrderItemEntity> entity);
    
    
    @Mapping(target = "id", ignore = true) // O ID é gerado pelo servidor
    @Mapping(target = "order", ignore = true) // A entidade Order é setada pelo serviço
    @Mapping(target = "produto", ignore = true) // A entidade Produto é setada pelo serviço
    @Mapping(target = "priceAtSale", ignore = true) // O preço é calculado pelo serviço
    void updatePut(OrderItemRequestDto request,@MappingTarget OrderItemEntity entity);
    
}
