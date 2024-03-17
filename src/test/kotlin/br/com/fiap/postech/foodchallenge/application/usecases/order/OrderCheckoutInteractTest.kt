package br.com.fiap.postech.foodchallenge.application.usecases.order

import br.com.fiap.postech.foodchallenge.application.gateways.CustomerGateway
import br.com.fiap.postech.foodchallenge.application.gateways.OrderGateway
import br.com.fiap.postech.foodchallenge.application.gateways.ProductGateway
import br.com.fiap.postech.foodchallenge.application.usecases.payment.CreatePaymentInteract
import br.com.fiap.postech.foodchallenge.domain.entities.CPF
import br.com.fiap.postech.foodchallenge.domain.entities.Category
import br.com.fiap.postech.foodchallenge.domain.entities.Customer
import br.com.fiap.postech.foodchallenge.domain.entities.Product
import br.com.fiap.postech.foodchallenge.domain.entities.order.Order
import br.com.fiap.postech.foodchallenge.domain.entities.order.OrderItem
import br.com.fiap.postech.foodchallenge.domain.entities.order.OrderStatus
import br.com.fiap.postech.foodchallenge.domain.exceptions.ProductNotFoundException
import br.com.fiap.postech.foodchallenge.infrastructure.controller.order.CheckoutRequest
import br.com.fiap.postech.foodchallenge.infrastructure.controller.order.OrderItemRequest
import br.com.fiap.postech.foodchallenge.infrastructure.controller.payment.CreatePaymentResponse
import org.hibernate.validator.internal.util.Contracts.assertNotNull
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import java.time.LocalDateTime

class OrderCheckoutInteractTest {

    private lateinit var orderGateway: OrderGateway
    private lateinit var customerGateway: CustomerGateway
    private lateinit var productGateway: ProductGateway
    private lateinit var createPaymentInteract: CreatePaymentInteract
    private lateinit var orderCheckoutInteract: OrderCheckoutInteract

    @BeforeEach
    fun setUp() {
        orderGateway = mock()
        productGateway = mock()
        createPaymentInteract = mock()
        orderCheckoutInteract = OrderCheckoutInteract(orderGateway, productGateway, createPaymentInteract)
    }

    @Test
    fun `should successfully complete checkout`() {
        val product = Product(1L, "Burger", "Delicious", "image.png", 10, Category.MAIN)
        val orderItemRequest = OrderItemRequest(1L, 2, "Extra cheese", true)
        val checkoutRequest = CheckoutRequest("12345678901", listOf(orderItemRequest))
        val order = Order(1L, CPF("12345678901"), listOf(OrderItem(1L, 2, "Extra cheese", true)), OrderStatus.RECEIVED, LocalDateTime.now())
        val createPaymentResponse = CreatePaymentResponse(10, "AA", "123")

        whenever(productGateway.findById(1L)).thenReturn(product)
        whenever(orderGateway.save(any())).thenReturn(order)
        whenever(createPaymentInteract.createPayment(any())).thenReturn(createPaymentResponse)

        val result = orderCheckoutInteract.checkout(checkoutRequest, "12345678901")

        assertNotNull(result)
        assertEquals(order.id, result.orderId)
    }

    @Test
    fun `should throw ProductNotFoundException for missing product`() {
        val orderItemRequest = OrderItemRequest(1L, 2, "Extra cheese", true)
        val checkoutRequest = CheckoutRequest("12345678901", listOf(orderItemRequest))

        whenever(productGateway.findById(1L)).thenReturn(null)

        assertThrows<ProductNotFoundException> {
            orderCheckoutInteract.checkout(checkoutRequest, "12345678901")
        }
    }

    @Test
    fun `should handle checkout with no customer found`() {
        val product = Product(1L, "Burger", "Delicious", "image.png", 10, Category.MAIN)
        val orderItemRequest = OrderItemRequest(1L, 2, "Extra cheese", true)
        val checkoutRequest = CheckoutRequest(null, listOf(orderItemRequest))
        val order = Order(1L, null, listOf(OrderItem(1L, 2, "Extra cheese", true)), OrderStatus.RECEIVED, LocalDateTime.now())
        val createPaymentResponse = CreatePaymentResponse(10, "AA", "123")

        whenever(productGateway.findById(1L)).thenReturn(product)
        whenever(orderGateway.save(any())).thenReturn(order)
        whenever(createPaymentInteract.createPayment(any())).thenReturn(createPaymentResponse)

        val result = orderCheckoutInteract.checkout(checkoutRequest, null)

        assertNotNull(result)
        assertNotNull(result.orderId)
    }
}