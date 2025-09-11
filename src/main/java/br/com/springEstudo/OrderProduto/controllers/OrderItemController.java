package br.com.springEstudo.OrderProduto.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.springEstudo.OrderProduto.business.OrderItemService;
import br.com.springEstudo.OrderProduto.business.dto.OrderItemRequestDto;
import br.com.springEstudo.OrderProduto.business.dto.OrderItemResponseDto;

@RestController
@RequestMapping("/api/orders/{orderId}/items")
public class OrderItemController {
	@Autowired
    private OrderItemService orderItemService;

    // POST /api/orders/{orderId}/items
    // Adiciona um novo item a um pedido existente
    @PostMapping
    public ResponseEntity<OrderItemResponseDto> createOrderItem(
            @PathVariable UUID orderId,
            @RequestBody OrderItemRequestDto requestDto) {
        OrderItemResponseDto createdItem = orderItemService.createOrderItem(orderId, requestDto);
        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    // GET /api/orders/{orderId}/items
    // Lista todos os itens de um pedido específico
    @GetMapping
    public ResponseEntity<List<OrderItemResponseDto>> findAllByOrderId(@PathVariable UUID orderId) {
        List<OrderItemResponseDto> items = orderItemService.findAllByOrderId(orderId);
        return ResponseEntity.ok(items);
    }

    // GET /api/orders/{orderId}/items/{itemId}
    // Busca um item específico em um pedido
    @GetMapping("/{itemId}")
    public ResponseEntity<OrderItemResponseDto> findById(
            @PathVariable UUID orderId, 
            @PathVariable UUID itemId) {
        OrderItemResponseDto item = orderItemService.findById(orderId, itemId);
        return ResponseEntity.ok(item);
    }
    
    // PUT /api/orders/{orderId}/items/{itemId}
    // Atualiza um item em um pedido (ex: a quantidade)
    @PutMapping("/{itemId}")
    public ResponseEntity<OrderItemResponseDto> updateOrderItem(
            @PathVariable UUID orderId, 
            @PathVariable UUID itemId,
            @RequestBody OrderItemRequestDto requestDto) {
        OrderItemResponseDto updatedItem = orderItemService.updateOrderItem(orderId, itemId, requestDto);
        return ResponseEntity.ok(updatedItem);
    }

    // DELETE /api/orders/{orderId}/items/{itemId}
    // Deleta um item de um pedido
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID orderId, @PathVariable UUID itemId) {
        orderItemService.deleteById(orderId, itemId);
        return ResponseEntity.noContent().build();
    }
}
