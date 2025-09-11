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

import br.com.springEstudo.OrderProduto.business.OrderService;
import br.com.springEstudo.OrderProduto.business.dto.OrderRequestDto;
import br.com.springEstudo.OrderProduto.business.dto.OrderResponseDto;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // POST /api/orders
    // Cria um novo pedido completo
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto requestDto) {
        OrderResponseDto createdOrder = orderService.createOrder(requestDto);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }
    
    // GET /api/orders
    // Lista todos os pedidos
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> findAll() {
        List<OrderResponseDto> orders = orderService.findAll();
        return ResponseEntity.ok(orders);
    }

    // GET /api/orders/{id}
    // Busca um pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> findById(@PathVariable UUID id) {
        OrderResponseDto order = orderService.findById(id);
        return ResponseEntity.ok(order);
    }
    
    // PUT /api/orders/{id}
    // Atualiza dados gerais de um pedido
    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDto> updateOrder(
            @PathVariable UUID id, 
            @RequestBody OrderRequestDto requestDto) {
        OrderResponseDto updatedOrder = orderService.update(id, requestDto);
        return ResponseEntity.ok(updatedOrder);
    }

    // DELETE /api/orders/{id}
    // Deleta um pedido por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id) {
        orderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
