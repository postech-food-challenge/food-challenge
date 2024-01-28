package br.com.fiap.postech.foodchallenge.application.usecases.payment

import br.com.fiap.postech.foodchallenge.application.gateways.OrderGateway
import br.com.fiap.postech.foodchallenge.domain.entities.CPF
import br.com.fiap.postech.foodchallenge.domain.entities.order.Order
import br.com.fiap.postech.foodchallenge.domain.entities.order.OrderItem
import br.com.fiap.postech.foodchallenge.domain.entities.order.OrderStatus
import br.com.fiap.postech.foodchallenge.domain.exceptions.OrderNotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.time.LocalDateTime

class FindPaymentByOrderInteractTest {

    private lateinit var gateway: OrderGateway
    private lateinit var findPaymentByOrderIdInteract: FindPaymentByOrderIdInteract

    @BeforeEach
    fun setUp() {
        gateway = mock()
        findPaymentByOrderIdInteract = FindPaymentByOrderIdInteract(gateway)
    }

    @Test
    fun `should successfully get a payment`() {
        val order = Order(
            1L,
            CPF("12345678901"),
            listOf(OrderItem(1L, 2, "Hamburguer", true)),
            OrderStatus.RECEIVED,
            LocalDateTime.now(),
            false,
            20
        )
        whenever(gateway.findById(1L)).thenReturn(order)

        val result = findPaymentByOrderIdInteract.findPaymentByOrderId(1L)

        assertEquals(order, result)
    }

    @Test
    fun `should throw exception when getting a payment and the order does not exist`() {
        val order = Order(
            1L,
            CPF("12345678901"),
            listOf(OrderItem(1L, 2, "Hamburguer", true)),
            OrderStatus.RECEIVED,
            LocalDateTime.now(),
            false,
            20
        )
        whenever(gateway.findById(1L)).thenReturn(order)

        assertThrows<OrderNotFoundException> { findPaymentByOrderIdInteract.findPaymentByOrderId(2L) }
    }
}