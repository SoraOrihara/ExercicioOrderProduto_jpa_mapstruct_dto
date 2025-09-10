package br.com.springEstudo.OrderProduto.business.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import br.com.springEstudo.OrderProduto.business.dto.ProdutoRequestDto;
import br.com.springEstudo.OrderProduto.business.dto.ProdutoResponseDto;
import br.com.springEstudo.OrderProduto.insfraestructure.entities.ProdutoEntity;

@Mapper(componentModel = "string")
public interface ProdutoMapper {
	
	@Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
	ProdutoEntity paraProdutoEntity (ProdutoRequestDto request);
	
	ProdutoResponseDto paraProdutoResponseDto(ProdutoEntity entity);
	
	@Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
	@Mapping(target="id",ignore=true)
	void updatePut(ProdutoRequestDto request,@MappingTarget ProdutoEntity entity);
	
}
