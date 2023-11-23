package br.com.fiap.postech.foodchallenge.application.configuration

import br.com.fiap.postech.foodchallenge.adapters.controller.dto.CustomerRequest
import br.com.fiap.postech.foodchallenge.adapters.persistence.entities.OrderEntity
import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.Customer
import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.Order
import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.OrderItem
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.treeToValue

fun CustomerRequest.toDomain(): Customer {
    return Customer.createCustomer(this)
}

fun OrderEntity.toDomain(objectMapper: ObjectMapper): Order {
    val items: List<OrderItem> = objectMapper.treeToValue(this.itemsData)
    return Order(this.id, this.customerId, items, this.status)
}

fun Order.toEntity(objectMapper: ObjectMapper): OrderEntity {
    val itemsData = objectMapper.valueToTree<JsonNode>(this.items)
    return OrderEntity(this.id, this.customerId, itemsData, this.status)
}