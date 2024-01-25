package br.com.fiap.postech.foodchallenge.application.domain.services

import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.Order
import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.Order.Companion.createOrder
import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.OrderEntityComparator
import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.OrderItem
import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.OrderStatus
import br.com.fiap.postech.foodchallenge.configuration.toDomain
import br.com.fiap.postech.foodchallenge.configuration.toEntity
import br.com.fiap.postech.foodchallenge.domain.exceptions.NoObjectFoundException
import br.com.fiap.postech.foodchallenge.domain.exceptions.ProductNotFoundException
import br.com.fiap.postech.foodchallenge.infrastructure.controller.dto.CheckoutRequest
import br.com.fiap.postech.foodchallenge.infrastructure.controller.dto.CheckoutResponse
import br.com.fiap.postech.foodchallenge.infrastructure.persistence.CustomerRepository
import br.com.fiap.postech.foodchallenge.infrastructure.persistence.OrderRepository
import br.com.fiap.postech.foodchallenge.infrastructure.persistence.ProductRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository,
    private val customerRepository: CustomerRepository,
    private val objectMapper: ObjectMapper
) {

    fun createOrder(request: CheckoutRequest): CheckoutResponse {
        val items = request.items.map { itemRequest ->
            val product = productRepository.findById(itemRequest.productId)
                .orElseThrow { ProductNotFoundException(itemRequest.productId) }

            product.id?.let {
                OrderItem(it, itemRequest.quantity, itemRequest.observations, itemRequest.toGo)
            } ?: throw IllegalStateException("Product ID should not be null.")
        }

        val customer = request.customerId?.let { customerRepository.findById(it).orElse(null) }

        val order = createOrder(customer?.id, items)

        val savedEntity = orderRepository.save(order.toEntity(objectMapper))
        val savedOrder = savedEntity.toDomain(objectMapper)

        return CheckoutResponse(
            savedOrder.id ?: throw IllegalStateException("Saved order ID should not be null."),
            savedOrder.status
        )
    }

    fun getOrders(status: String?): List<Order> {
        status ?.let {
            return findOrdersByGivenStatus(it)
        }
        return findOrders();
    }

    private fun findOrdersByGivenStatus(status: String): List<Order> {
        val orders = status
            .takeIf { it.isNotEmpty() }
            ?.let { OrderStatus.validateStatus(it) }
            ?.let { orderRepository.findByStatus(it) }
            ?: orderRepository.findAll()

        return orders.takeIf { it.isNotEmpty() }
            ?.map { it.toDomain(objectMapper) }
            ?: throw NoObjectFoundException("No orders found.")
    }

    private fun findOrders(): List<Order> {
        val orders = orderRepository.findByStatusIn(
            listOf(OrderStatus.READY, OrderStatus.IN_PREPARATION, OrderStatus.RECEIVED)
        )

        return orders.takeIf { it.isNotEmpty() }
            ?.sortedWith(OrderEntityComparator())
            ?.map { it.toDomain(objectMapper) }
            ?: throw NoObjectFoundException("No orders found.")
    }
}