package br.com.fiap.postech.foodchallenge.application.domain.services

import br.com.fiap.postech.foodchallenge.adapters.controller.dto.CheckoutRequest
import br.com.fiap.postech.foodchallenge.adapters.persistence.CustomerRepository
import br.com.fiap.postech.foodchallenge.adapters.persistence.OrderRepository
import br.com.fiap.postech.foodchallenge.adapters.persistence.ProductRepository
import br.com.fiap.postech.foodchallenge.application.domain.exceptions.ProductNotFoundException
import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.Order
import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.OrderItem
import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.OrderStatus.RECEIVED
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository,
    private val customerRepository: CustomerRepository
) {

    fun createOrder(request: CheckoutRequest): Order {
        val items = request.items.map { itemRequest ->
            val product = productRepository.findById(itemRequest.productId).orElse(null)
                ?: throw ProductNotFoundException(itemRequest.productId)
            OrderItem(product, itemRequest.quantity)
        }

        val customer = request.customerId?.let { customerRepository.findById(it).orElse(null) }

        val order = Order(customer = customer, items = items, status = RECEIVED)

        return orderRepository.save(order)
    }

}