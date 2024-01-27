package br.com.fiap.postech.foodchallenge.application.usecases.order

import br.com.fiap.postech.foodchallenge.application.gateways.OrderGateway
import br.com.fiap.postech.foodchallenge.domain.entities.order.OrderStatus
import br.com.fiap.postech.foodchallenge.domain.entities.order.comparator.OrderComparator
import br.com.fiap.postech.foodchallenge.domain.exceptions.InvalidParameterException
import br.com.fiap.postech.foodchallenge.domain.exceptions.NoObjectFoundException
import br.com.fiap.postech.foodchallenge.infrastructure.controller.order.OrderResponse

class ListOrdersInteract(private val orderGateway: OrderGateway) {

    fun getOrders(status: String?): List<OrderResponse> {
        status ?.let {
            return findOrdersByGivenStatus(it)
        }
        return findOrders()
    }

    private fun findOrdersByGivenStatus(status: String): List<OrderResponse> {
        val orders = try {
            val validStatus = OrderStatus.validateStatus(status)
            orderGateway.findByStatus(validStatus)
        } catch (ipe : InvalidParameterException) {
            throw ipe
        }

        return orders.takeIf { it.isNotEmpty() }
            ?.mapNotNull { order ->
                order.let { OrderResponse.fromDomain(it) }
            }
            ?: throw NoObjectFoundException("No orders found.")
    }

    private fun findOrders(): List<OrderResponse> {
        val orders = orderGateway.findInStatus(
            listOf(OrderStatus.READY, OrderStatus.IN_PREPARATION, OrderStatus.RECEIVED)
        )

        return orders.takeIf { it.isNotEmpty() }
            ?.sortedWith(OrderComparator())
            ?.mapNotNull { order ->
                order.let { OrderResponse.fromDomain(it) } }
            ?: throw NoObjectFoundException("No orders found.")
    }
}

