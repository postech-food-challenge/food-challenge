package br.com.fiap.postech.foodchallenge.infrastructure.controller.order

import br.com.fiap.postech.foodchallenge.domain.entities.order.Order
import java.time.LocalDateTime

data class OrderResponse(
    val orderId: Long,
    val cpf: String?,
    val items: List<OrderItemResponse>,
    val status: String,
    val createdAt: LocalDateTime,
    val qrData: String? = null,
    val inStoreOrderId: String? = null,
) {
    companion object {
        fun fromDomain(domainObject: Order) =
            domainObject.id?.let { order ->
                OrderResponse(
                    order,
                    domainObject.customerCpf?.value,
                    domainObject.items.map { orderItem -> OrderItemResponse.fromDomain(orderItem) },
                    domainObject.status.name,
                    domainObject.createdAt,
                    domainObject.qrData,
                    domainObject.inStoreOrderId
                )
            }
    }
}
