package br.com.fiap.postech.foodchallenge.application.domain.model.aggregates

import br.com.fiap.postech.foodchallenge.adapters.persistence.entities.OrderEntity
import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.OrderStatus.RECEIVED
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper

data class Order(
    val id: Long? = null,
    val customerId: Long? = null,
    val items: List<OrderItem>,
    val status: OrderStatus
) {
    companion object {
        fun createOrder(customerId: Long?, items: List<OrderItem>): Order {
            return Order(customerId = customerId, items = items, status = RECEIVED)
        }
    }
}

data class OrderItem(
    val productId: Long,
    val quantity: Int
)

enum class OrderStatus {
    RECEIVED, IN_PREPARATION, READY, COMPLETED, CANCELED
}

fun Order.toEntity(objectMapper: ObjectMapper): OrderEntity {
    val itemsData = objectMapper.valueToTree<JsonNode>(this.items)
    return OrderEntity(this.id, this.customerId, itemsData, this.status)
}