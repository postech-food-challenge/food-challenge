package br.com.fiap.postech.foodchallenge.infrastructure.controller.order

import br.com.fiap.postech.foodchallenge.domain.entities.order.OrderItem

data class OrderItemResponse(
    val productId: Long,
    val quantity: Int,
    val observations: String? = null,
    val toGo: Boolean
) {
    companion object {
        fun fromDomain(domainObject: OrderItem) = OrderItemResponse(
            domainObject.productId,
            domainObject.quantity,
            domainObject.observations,
            domainObject.toGo
        )
    }
}
