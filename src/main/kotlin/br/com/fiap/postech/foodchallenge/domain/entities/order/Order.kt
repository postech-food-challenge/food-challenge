package br.com.fiap.postech.foodchallenge.domain.entities.order

import br.com.fiap.postech.foodchallenge.domain.entities.CPF
import br.com.fiap.postech.foodchallenge.infrastructure.persistence.entities.OrderEntity
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.treeToValue

data class Order(
    val id: Long? = null,
    val customerCpf: CPF? = null,
    val items: List<OrderItem>,
    val status: OrderStatus
) {
    companion object {
        fun createOrder(customerId: CPF?, items: List<OrderItem>): Order {
            return Order(customerCpf = customerId, items = items, status = OrderStatus.RECEIVED)
        }

        fun fromEntity(entity: OrderEntity, objectMapper: ObjectMapper): Order {
            val items: List<OrderItem> = objectMapper.treeToValue(entity.itemsData)
            return Order(entity.id, entity.customerCpf?.let { CPF(it) }, items, entity.status)
        }
    }
}
