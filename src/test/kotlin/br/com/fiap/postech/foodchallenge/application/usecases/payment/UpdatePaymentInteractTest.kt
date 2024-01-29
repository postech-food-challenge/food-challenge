package br.com.fiap.postech.foodchallenge.application.usecases.payment

import br.com.fiap.postech.foodchallenge.application.gateways.OrderGateway
import br.com.fiap.postech.foodchallenge.domain.entities.CPF
import br.com.fiap.postech.foodchallenge.domain.entities.Payment
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

class UpdatePaymentInteractTest {

    private lateinit var updatePaymentInteract: UpdatePaymentInteract
    private lateinit var orderGateway: OrderGateway

    @BeforeEach
    fun setUp() {
        orderGateway = mock()
        updatePaymentInteract = UpdatePaymentInteract(orderGateway)
    }

    @Test
    fun `should successfully update a payment`() {
        val order = Order(
            1L,
            CPF("12345678901"),
            listOf(OrderItem(1L, 2, "Hamburguer", true)),
            OrderStatus.RECEIVED,
            LocalDateTime.now(),
            true,
            20
        )

        val payment = Payment(1L, true)

        whenever(orderGateway.findById(1L)).thenReturn(order)
        whenever(orderGateway.save(order)).thenReturn(order)

        val result = updatePaymentInteract.updatePaymentStatusByOrderId(payment)

        assertEquals(order.paymentValidated, result.paymentValidated)
    }

    @Test
    fun `should throw exception when updating a order that does not exist`() {
        val order = Order(
            1L,
            CPF("12345678901"),
            listOf(OrderItem(1L, 2, "Hamburguer", true)),
            OrderStatus.RECEIVED,
            LocalDateTime.now(),
            true,
            20
        )

        val payment = Payment(2L, true)

        whenever(orderGateway.findById(1L)).thenReturn(order)
        whenever(orderGateway.save(order)).thenReturn(order)

        assertThrows<OrderNotFoundException> { updatePaymentInteract.updatePaymentStatusByOrderId(payment) }
    }
}