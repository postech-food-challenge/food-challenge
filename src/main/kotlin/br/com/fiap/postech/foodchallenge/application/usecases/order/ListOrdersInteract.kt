package br.com.fiap.postech.foodchallenge.application.usecases.order

import br.com.fiap.postech.foodchallenge.application.gateways.OrderGateway
import br.com.fiap.postech.foodchallenge.domain.entities.order.OrderStatus
import br.com.fiap.postech.foodchallenge.domain.exceptions.NoObjectFoundException
import br.com.fiap.postech.foodchallenge.infrastructure.controller.order.OrderResponse

class ListOrdersInteract(private val orderGateway: OrderGateway) {

    fun getOrders(status: String?): List<OrderResponse> {
        val orders = when {
            status.isNullOrEmpty() -> orderGateway.findActiveOrdersSorted()
            else -> OrderStatus.validateStatus(status)
                .let { orderGateway.findByStatus(it) }
        }

        return orders.takeIf { it.isNotEmpty() }
            ?.mapNotNull { order -> OrderResponse.fromDomain(order) }
            ?: throw NoObjectFoundException("No orders found.")
    }
}

