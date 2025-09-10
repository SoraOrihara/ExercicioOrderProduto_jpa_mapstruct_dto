package br.com.springEstudo.OrderProduto.insfraestructure.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.springEstudo.OrderProduto.insfraestructure.entities.OrderEntity;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {

}
