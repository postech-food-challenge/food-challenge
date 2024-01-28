package br.com.fiap.postech.foodchallenge.application.usecases.payment

import br.com.fiap.postech.foodchallenge.application.gateways.OrderGateway
import br.com.fiap.postech.foodchallenge.domain.entities.CPF
import br.com.fiap.postech.foodchallenge.domain.entities.Payment
import br.com.fiap.postech.foodchallenge.domain.entities.order.Order
import br.com.fiap.postech.foodchallenge.domain.entities.order.OrderItem
import br.com.fiap.postech.foodchallenge.domain.entities.order.OrderStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

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
        val order = Order(1L, CPF("12345678901"), listOf(OrderItem(1L, 2, "Extra cheese", true)), OrderStatus.RECEIVED, false, 20)
        whenever(gateway.findById(1L)).thenReturn(order)

        val result = findPaymentByOrderIdInteract.findPaymentByOrderId(1L)

        assertEquals(order, result)
    }
}