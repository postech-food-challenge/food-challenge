package br.com.fiap.postech.foodchallenge.application.domain.services

import br.com.fiap.postech.foodchallenge.adapters.controller.dto.CheckoutRequest
import br.com.fiap.postech.foodchallenge.adapters.persistence.CustomerRepository
import br.com.fiap.postech.foodchallenge.adapters.persistence.OrderRepository
import br.com.fiap.postech.foodchallenge.adapters.persistence.ProductRepository
import br.com.fiap.postech.foodchallenge.adapters.persistence.entities.toDomain
import br.com.fiap.postech.foodchallenge.application.domain.exceptions.ProductNotFoundException
import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.Order
import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.OrderItem
import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.OrderStatus.RECEIVED
import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.toEntity
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository,
    private val customerRepository: CustomerRepository,
    private val objectMapper: ObjectMapper
) {

    fun createOrder(request: CheckoutRequest): Order {
        val items = request.items.map { itemRequest ->
            val product = productRepository.findById(itemRequest.productId)
                .orElseThrow { ProductNotFoundException(itemRequest.productId) }

            product.id?.let {
                OrderItem(it, itemRequest.quantity)
            } ?: throw IllegalStateException("Product ID should not be null.")
        }

        val customer = request.customerId?.let { customerRepository.findById(it).orElse(null) }

        val order = Order(customerId = customer?.id, items = items, status = RECEIVED)

        val savedEntity = orderRepository.save(order.toEntity(objectMapper))

        return savedEntity.toDomain(objectMapper)
    }


}