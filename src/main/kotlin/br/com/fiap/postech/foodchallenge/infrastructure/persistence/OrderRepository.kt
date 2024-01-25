package br.com.fiap.postech.foodchallenge.infrastructure.persistence

import br.com.fiap.postech.foodchallenge.infrastructure.persistence.entities.OrderEntity
import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.OrderStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<OrderEntity, Long> {

    fun findByStatus(status: OrderStatus) : List<OrderEntity>

}