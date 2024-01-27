package br.com.fiap.postech.foodchallenge.infrastructure.gateways

import br.com.fiap.postech.foodchallenge.application.gateways.OrderGateway
import br.com.fiap.postech.foodchallenge.domain.entities.order.Order
import br.com.fiap.postech.foodchallenge.domain.entities.order.OrderStatus
import br.com.fiap.postech.foodchallenge.infrastructure.persistence.OrderRepository
import br.com.fiap.postech.foodchallenge.infrastructure.persistence.entities.OrderEntity
import com.fasterxml.jackson.databind.ObjectMapper

class OrderRepositoryGateway(
    private val orderRepository: OrderRepository,
    private val objectMapper: ObjectMapper
) : OrderGateway {
    override fun save(order: Order): Order =
        OrderEntity.fromDomain(order, objectMapper).run {
            orderRepository.save(this)
        }.let { Order.fromEntity(it, objectMapper) }

    override fun findAll(): List<Order> =
        orderRepository.findAll().mapNotNull { Order.fromEntity(it, objectMapper) }

    override fun findByStatus(status: OrderStatus) =
        orderRepository.findByStatus(status).map { Order.fromEntity(it, objectMapper) }
}