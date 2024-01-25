package br.com.fiap.postech.foodchallenge.adapters.persistence

import br.com.fiap.postech.foodchallenge.adapters.persistence.entities.OrderEntity
import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.OrderStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<OrderEntity, Long> {

    fun findByStatus(status: OrderStatus) : List<OrderEntity>

    fun findByStatusIn(status: List<OrderStatus>) : List<OrderEntity>
}