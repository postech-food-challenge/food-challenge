package br.com.fiap.postech.foodchallenge.infrastructure.controller.order

import br.com.fiap.postech.foodchallenge.domain.entities.order.Order

data class OrderResponse(
    val orderId: Long,
    val status: String
) {
    companion object {
        fun fromDomain(domainObject: Order) = domainObject.id?.let { OrderResponse(it, domainObject.status.name) }
    }
}
