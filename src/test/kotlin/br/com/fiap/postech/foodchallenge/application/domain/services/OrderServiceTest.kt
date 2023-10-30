package br.com.fiap.postech.foodchallenge.application.domain.services

import br.com.fiap.postech.foodchallenge.adapters.controller.dto.CheckoutRequest
import br.com.fiap.postech.foodchallenge.adapters.controller.dto.OrderItemRequest
import br.com.fiap.postech.foodchallenge.adapters.persistence.CustomerRepository
import br.com.fiap.postech.foodchallenge.adapters.persistence.OrderRepository
import br.com.fiap.postech.foodchallenge.adapters.persistence.ProductRepository
import br.com.fiap.postech.foodchallenge.application.domain.exceptions.ProductNotFoundException
import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.Order.Companion.createOrder
import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.OrderItem
import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.OrderStatus
import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.toEntity
import br.com.fiap.postech.foodchallenge.application.domain.model.entities.Product
import br.com.fiap.postech.foodchallenge.application.domain.model.entities.ProductCategoryEnum.MAIN
import br.com.fiap.postech.foodchallenge.application.domain.model.entities.ProductCategoryEnum.SIDE
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.*

class OrderServiceTest {

    private lateinit var productRepository: ProductRepository
    private lateinit var orderRepository: OrderRepository
    private lateinit var customerRepository: CustomerRepository
    private lateinit var objectMapper: ObjectMapper
    private lateinit var orderService: OrderService

    @BeforeEach
    fun setUp() {
        productRepository = mock()
        orderRepository = mock()
        customerRepository = mock()
        objectMapper = jacksonObjectMapper()
            .registerModule(
                KotlinModule.Builder()
                    .withReflectionCacheSize(512)
                    .configure(KotlinFeature.NullToEmptyCollection, false)
                    .configure(KotlinFeature.NullToEmptyMap, false)
                    .configure(KotlinFeature.NullIsSameAsDefault, false)
                    .configure(KotlinFeature.SingletonSupport, true)
                    .configure(KotlinFeature.StrictNullChecks, false)
                    .build()
            )
        orderService = OrderService(productRepository, orderRepository, customerRepository, objectMapper)

        val tanjiroProduct =
            Product(1L, "Tanjiro's Earrings", "Demon Slayer Earrings", "imageUrl", 50, MAIN)
        val nezukoProduct =
            Product(2L, "Nezuko's Bamboo", "Bamboo mouthpiece", "imageUrl", 20, SIDE)

        whenever(productRepository.findById(1L)).thenReturn(Optional.of(tanjiroProduct))
        whenever(productRepository.findById(2L)).thenReturn(Optional.of(nezukoProduct))
    }

    @Test
    fun `should create order and return checkout response`() {
        val items = listOf(
            OrderItemRequest(productId = 1L, quantity = 2, toGo = false),
            OrderItemRequest(productId = 2L, quantity = 1, toGo = false)
        )
        val request = CheckoutRequest(1L, items)

        val tanjiroOrderItem = OrderItem(productId = 1L, quantity = 2, toGo = false)
        val nezukoOrderItem = OrderItem(productId = 2L, quantity = 1, toGo = false)
        val createdOrder = createOrder(1L, listOf(tanjiroOrderItem, nezukoOrderItem))
        val orderEntity = createdOrder.toEntity(objectMapper)

        doReturn(orderEntity.copy(id = 1L)).whenever(orderRepository).save(any())

        val response = orderService.createOrder(request)

        assertEquals(response.status, OrderStatus.RECEIVED)
        assertEquals(response.orderId, 1L)
    }

    @Test
    fun `should throw ProductNotFoundException when product is not found`() {
        val items = listOf(OrderItemRequest(productId = 3L, quantity = 2, toGo = false))
        val request = CheckoutRequest(1L, items)

        whenever(productRepository.findById(3L)).thenReturn(Optional.empty())

        assertThrows<ProductNotFoundException> {
            orderService.createOrder(request)
        }
    }

}