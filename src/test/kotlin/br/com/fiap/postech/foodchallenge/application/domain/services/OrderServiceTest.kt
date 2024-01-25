package br.com.fiap.postech.foodchallenge.application.domain.services

import br.com.fiap.postech.foodchallenge.adapters.controller.dto.CheckoutRequest
import br.com.fiap.postech.foodchallenge.adapters.controller.dto.OrderItemRequest
import br.com.fiap.postech.foodchallenge.adapters.persistence.CustomerRepository
import br.com.fiap.postech.foodchallenge.adapters.persistence.OrderRepository
import br.com.fiap.postech.foodchallenge.adapters.persistence.ProductRepository
import br.com.fiap.postech.foodchallenge.adapters.persistence.entities.OrderEntity
import br.com.fiap.postech.foodchallenge.application.configuration.toEntity
import br.com.fiap.postech.foodchallenge.application.domain.exceptions.InvalidParameterException
import br.com.fiap.postech.foodchallenge.application.domain.exceptions.NoObjectFoundException
import br.com.fiap.postech.foodchallenge.application.domain.exceptions.ProductNotFoundException
import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.Order.Companion.createOrder
import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.OrderItem
import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.OrderStatus
import br.com.fiap.postech.foodchallenge.application.domain.model.entities.Product
import br.com.fiap.postech.foodchallenge.application.domain.model.entities.ProductCategoryEnum.MAIN
import br.com.fiap.postech.foodchallenge.application.domain.model.entities.ProductCategoryEnum.SIDE
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.*
import java.time.LocalDateTime
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

        val items = listOf(
            OrderItem(
                productId = 1L,
                quantity = 2,
                observations = "teste",
                toGo = false
            )
        )

        val arrayNode: ArrayNode = objectMapper.valueToTree(items)

        val orderEntity = OrderEntity(
            id = 1L,
            customerId = 1L,
            itemsData = arrayNode,
            status = OrderStatus.RECEIVED,
            LocalDateTime.now()
        )

        whenever(productRepository.findById(1L)).thenReturn(Optional.of(tanjiroProduct))
        whenever(productRepository.findById(2L)).thenReturn(Optional.of(nezukoProduct))
        whenever(orderRepository.findAll()).thenReturn(listOf(orderEntity))
        whenever(orderRepository.findByStatus(OrderStatus.RECEIVED)).thenReturn(listOf(orderEntity))
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

    @Test
    fun `getOrders - should get a list of orders when receiving a status`() {
        val ordersList = orderService.getOrders("RECEIVED")

        verify(orderRepository).findByStatus(OrderStatus.RECEIVED)

        assert(ordersList.first().status == OrderStatus.RECEIVED)
    }

//    @Test
//    fun `getOrders - should get a list of orders when not receiving a status`() {
//        val ordersList = orderService.getOrders(null)
//
//        verify(orderRepository).findAll()
//
//        assert(ordersList.first().status == OrderStatus.RECEIVED)
//    }

    @Test
    fun `getOrders - should throw InvalidParameterException when getting a list of orders and receiving a wrong status`() {
        assertThrows<InvalidParameterException> { orderService.getOrders("RECEIV") }

        verify(orderRepository, never()).findByStatus(any())
    }

    @Test
    fun `getOrders - should throw NoObjectFoundException when getting a empty list of orders`() {
        whenever(orderRepository.findAll()).thenReturn(emptyList())

        assertThrows<NoObjectFoundException> { orderService.getOrders(null) }
    }
}