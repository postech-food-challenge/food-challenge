package br.com.fiap.postech.foodchallenge.application.domain.model.aggregates

import br.com.fiap.postech.foodchallenge.application.domain.model.entities.Product

data class Order(
    val id: OrderId,
    val customerId: Long?,
    val items: List<OrderItem>,
    val status: OrderStatus
) {
    fun addItem(item: OrderItem): Order {
        val updatedItems = items + item
        return copy(items = updatedItems)
    }

    fun updateStatus(newStatus: OrderStatus): Order {
        return copy(status = newStatus)
    }
}

data class OrderId(val value: String)

// Value Object?
data class OrderItem(
    val product: Product,
    val quantity: Int
)

enum class OrderStatus {
    RECEIVED, IN_PREPARATION, READY, COMPLETED, CANCELED
}
