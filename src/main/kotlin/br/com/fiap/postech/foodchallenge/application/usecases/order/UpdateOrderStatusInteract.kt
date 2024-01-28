package br.com.fiap.postech.foodchallenge.application.usecases.order

import br.com.fiap.postech.foodchallenge.application.gateways.OrderGateway
import br.com.fiap.postech.foodchallenge.domain.entities.order.Order
import br.com.fiap.postech.foodchallenge.domain.exceptions.NoObjectFoundException

class UpdateOrderStatusInteract(private val orderGateway: OrderGateway) {
    fun updateOrderStatus(id: Long, newStatus: String): Order {
        return orderGateway.findById(id)?.let {
            orderGateway.save(it.withUpdatedStatus(newStatus))
        } ?: throw NoObjectFoundException("No order found for id = $id")
    }
}
