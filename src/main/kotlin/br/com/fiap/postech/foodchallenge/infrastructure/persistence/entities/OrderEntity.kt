package br.com.fiap.postech.foodchallenge.infrastructure.persistence.entities

import br.com.fiap.postech.foodchallenge.domain.entities.order.Order
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import io.hypersistence.utils.hibernate.type.json.JsonType
import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY
import org.hibernate.annotations.Type
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
data class OrderEntity(
    @Id @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,

    @Column(name = "customer_cpf")
    val customerCpf: String?,

    @Type(JsonType::class)
    @Column(name = "items_data", columnDefinition = "jsonb")
    val itemsData: JsonNode,

    val status: String,

    @Column(name = "createdAt")
    val createdAt: LocalDateTime
) {
    companion object {
        fun fromDomain(domainObject: Order, objectMapper: ObjectMapper): OrderEntity {
            val itemsData = objectMapper.valueToTree<JsonNode>(domainObject.items)
            return OrderEntity(
                id = domainObject.id,
                customerCpf = domainObject.customerCpf?.value,
                itemsData = itemsData,
                status = domainObject.status.name,
                domainObject.createdAt
            )
        }
    }
}