package br.com.springEstudo.OrderProduto.business.mapstruct;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import br.com.springEstudo.OrderProduto.business.dto.ProdutoRequestDto;
import br.com.springEstudo.OrderProduto.business.dto.ProdutoResponseDto;
import br.com.springEstudo.OrderProduto.insfraestructure.entities.ProdutoEntity;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {
	
	@Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
	ProdutoEntity paraProdutoEntity (ProdutoRequestDto request);
	
	ProdutoResponseDto paraProdutoResponseDto(ProdutoEntity entity);
	
	List<ProdutoResponseDto> paraListProdutoResponseDto(List<ProdutoEntity> entity);
	
	@Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
	@Mapping(target="id",ignore=true)
	void updatePut(ProdutoRequestDto request,@MappingTarget ProdutoEntity entity);
	
}
