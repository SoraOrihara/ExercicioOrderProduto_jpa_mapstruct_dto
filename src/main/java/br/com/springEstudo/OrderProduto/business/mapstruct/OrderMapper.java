package br.com.springEstudo.OrderProduto.business.mapstruct;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import br.com.springEstudo.OrderProduto.business.dto.OrderRequestDto;
import br.com.springEstudo.OrderProduto.business.dto.OrderResponseDto;
import br.com.springEstudo.OrderProduto.insfraestructure.entities.OrderEntity;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {

	@Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
	@Mapping(target="itens",ignore=true)
	OrderEntity paraOrderEntity(OrderRequestDto request);
	
	OrderResponseDto paraOrderResponseDto(OrderEntity entity);
	
	List<OrderResponseDto> paraListaOrderResponseDto(List<OrderEntity> entity);
	
	@Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "itens", ignore = true)
    void updateEntityFromDto(OrderRequestDto requestDto, @MappingTarget OrderEntity entity);
}
