package br.com.fiap.postech.foodchallenge.adapters.persistence.entities

import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.Order
import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.OrderItem
import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.OrderStatus
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "orders")
data class OrderEntity(
    @Id @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,

    @Column(name = "customer_id")
    val customerId: Long?,

    @Column(name = "items_data", columnDefinition = "jsonb")
    val itemsData: String,

    @Enumerated(EnumType.STRING)
    val status: OrderStatus
)

fun OrderEntity.toDomain(objectMapper: ObjectMapper): Order {
    val items: List<OrderItem> = objectMapper.readValue(this.itemsData)
    return Order(this.id, this.customerId, items, this.status)
}
