package br.com.fiap.postech.foodchallenge.infrastructure.persistence.entities

import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.OrderStatus
import com.fasterxml.jackson.databind.JsonNode
import io.hypersistence.utils.hibernate.type.json.JsonType
import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY
import org.hibernate.annotations.Type

@Entity
@Table(name = "orders")
data class OrderEntity(
    @Id @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,

    @Column(name = "customer_id")
    val customerId: Long?,

    @Type(JsonType::class)
    @Column(name = "items_data", columnDefinition = "jsonb")
    val itemsData: JsonNode,

    @Enumerated(EnumType.STRING)
    val status: OrderStatus
)
