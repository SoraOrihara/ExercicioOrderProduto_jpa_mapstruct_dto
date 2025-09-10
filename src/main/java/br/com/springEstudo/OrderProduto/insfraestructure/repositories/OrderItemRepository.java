package br.com.springEstudo.OrderProduto.insfraestructure.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.springEstudo.OrderProduto.insfraestructure.entities.OrderItemEntity;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, UUID> {

}
