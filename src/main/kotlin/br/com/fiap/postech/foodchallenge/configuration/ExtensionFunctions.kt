package br.com.fiap.postech.foodchallenge.configuration

import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.Order
import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.OrderItem
import br.com.fiap.postech.foodchallenge.infrastructure.persistence.entities.OrderEntity
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.treeToValue
import java.time.LocalDateTime

fun OrderEntity.toDomain(objectMapper: ObjectMapper): Order {
    val items: List<OrderItem> = objectMapper.treeToValue(this.itemsData)
    return Order(this.id, this.customerId, items, this.status, this.createdAt)
}

fun Order.toEntity(objectMapper: ObjectMapper): OrderEntity {
    val itemsData = objectMapper.valueToTree<JsonNode>(this.items)
    return OrderEntity(this.id, this.customerId, itemsData, this.status, LocalDateTime.now())
}