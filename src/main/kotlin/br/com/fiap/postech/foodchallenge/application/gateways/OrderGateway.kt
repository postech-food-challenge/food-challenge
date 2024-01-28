package br.com.fiap.postech.foodchallenge.application.gateways

import br.com.fiap.postech.foodchallenge.domain.entities.order.Order
import br.com.fiap.postech.foodchallenge.domain.entities.order.OrderStatus

interface OrderGateway {
    fun save(order: Order): Order

    fun findAll(): List<Order>

    fun findByStatus(status: OrderStatus): List<Order>

    fun findActiveOrdersSorted(): List<Order>

    fun findById(id: Long): Order?
}