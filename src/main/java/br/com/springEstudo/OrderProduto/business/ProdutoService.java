package br.com.springEstudo.OrderProduto.business;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.springEstudo.OrderProduto.business.dto.ProdutoRequestDto;
import br.com.springEstudo.OrderProduto.business.dto.ProdutoResponseDto;
import br.com.springEstudo.OrderProduto.business.mapstruct.ProdutoMapper;
import br.com.springEstudo.OrderProduto.exception.ResourceNotFoundException;
import br.com.springEstudo.OrderProduto.insfraestructure.entities.ProdutoEntity;
import br.com.springEstudo.OrderProduto.insfraestructure.repositories.ProdutoRepository;

@Service
public class ProdutoService {

	private final ProdutoRepository produtoRepository;
	private final ProdutoMapper produtoMapper;
	
	
	public ProdutoService(ProdutoRepository produtoRepository,ProdutoMapper produtoMapper) {
		this.produtoRepository=produtoRepository;
		this.produtoMapper=produtoMapper;
	}
	
	public ProdutoResponseDto createProduto(ProdutoRequestDto request) {
		ProdutoEntity produto = produtoMapper.paraProdutoEntity(request);
		ProdutoEntity produtoSalvo = produtoRepository.save(produto);
		return produtoMapper.paraProdutoResponseDto(produtoSalvo);
	}
	
	public List<ProdutoResponseDto> findAll(){
		return produtoMapper.paraListProdutoResponseDto(produtoRepository.findAll());
	}
	
	public ProdutoResponseDto findById(UUID id) {
		ProdutoEntity produto=produtoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Id não encontrado: "+id));
		return produtoMapper.paraProdutoResponseDto(produto);
	}
	
	
	public ProdutoResponseDto updateProduto(UUID id, ProdutoRequestDto request) {
		ProdutoEntity produto = produtoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Id não encontrado: "+id));
		produtoMapper.updatePut(request, produto);
		ProdutoEntity produtoSalvo=produtoRepository.save(produto);
		return produtoMapper.paraProdutoResponseDto(produtoSalvo);
	}
	
	public void deleteById(UUID id) {
		if(!produtoRepository.existsById(id)) {
			throw new ResourceNotFoundException("Id não encontrada: "+id);
		}
		produtoRepository.deleteById(id);
	}
	
	
}
