package br.com.fiap.postech.foodchallenge.infrastructure.controller.order

import br.com.fiap.postech.foodchallenge.domain.entities.order.Order
import java.time.LocalDateTime

data class OrderResponse(
    val orderId: Long,
    val status: String,
    val createAt: LocalDateTime
) {
    companion object {
        fun fromDomain(domainObject: Order) = domainObject.id?.let { OrderResponse(it, domainObject.status.name, domainObject.createdAt) }
    }
}
