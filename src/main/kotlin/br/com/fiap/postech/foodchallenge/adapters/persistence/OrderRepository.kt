package br.com.fiap.postech.foodchallenge.adapters.persistence

import br.com.fiap.postech.foodchallenge.adapters.persistence.entities.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<OrderEntity, Long> {
}