package br.com.fiap.postech.foodchallenge.application.domain.services

import br.com.fiap.postech.foodchallenge.domain.entities.order.Order
import br.com.fiap.postech.foodchallenge.domain.exceptions.NoObjectFoundException
import br.com.fiap.postech.foodchallenge.infrastructure.controller.order.UpdateOrderStatusRequest
import br.com.fiap.postech.foodchallenge.infrastructure.persistence.OrderRepository
import br.com.fiap.postech.foodchallenge.infrastructure.persistence.entities.OrderEntity.Companion.updateStatus
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val objectMapper: ObjectMapper
) {

    fun updateOrderStatus(id: Long, newStatusWrapper: UpdateOrderStatusRequest): Order {
        val orderToUpdate =
            orderRepository.findById(id).orElse(null) ?: throw NoObjectFoundException("No order found for id = $id")
        val updatedOrder = orderToUpdate.updateStatus(newStatusWrapper.status)
        orderRepository.save(updatedOrder)

        return Order.fromEntity(updatedOrder, objectMapper)
    }
}