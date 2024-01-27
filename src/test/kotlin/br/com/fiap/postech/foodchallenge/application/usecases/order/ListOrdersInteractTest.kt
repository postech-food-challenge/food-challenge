package br.com.fiap.postech.foodchallenge.application.usecases.order

import br.com.fiap.postech.foodchallenge.application.gateways.OrderGateway
import br.com.fiap.postech.foodchallenge.domain.entities.CPF
import br.com.fiap.postech.foodchallenge.domain.entities.order.Order
import br.com.fiap.postech.foodchallenge.domain.entities.order.OrderItem
import br.com.fiap.postech.foodchallenge.domain.entities.order.OrderStatus
import br.com.fiap.postech.foodchallenge.domain.exceptions.NoObjectFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ListOrdersInteractTest {

    private lateinit var orderGateway: OrderGateway
    private lateinit var listOrdersInteract: ListOrdersInteract

    @BeforeEach
    fun setUp() {
        orderGateway = mock()
        listOrdersInteract = ListOrdersInteract(orderGateway)
    }

    @Test
    fun `should return orders for a specific status`() {
        val status = OrderStatus.RECEIVED
        val orders = listOf(
            Order(1L, CPF("12345678901"), createOrderItems(), OrderStatus.RECEIVED),
            Order(2L, CPF("23456789012"), createOrderItems(), OrderStatus.RECEIVED)
        )
        whenever(orderGateway.findByStatus(status)).thenReturn(orders)

        val result = listOrdersInteract.getOrders(status.name)

        assertNotNull(result)
        assertEquals(2, result.size)
    }

    @Test
    fun `should return all orders when no status is provided`() {
        val orders = listOf(
            Order(1L, CPF("12345678901"), createOrderItems(), OrderStatus.RECEIVED),
            Order(2L, CPF("23456789012"), createOrderItems(), OrderStatus.IN_PREPARATION),
            Order(3L, CPF("34567890123"), createOrderItems(), OrderStatus.READY)
        )
        whenever(orderGateway.findAll()).thenReturn(orders)

        val result = listOrdersInteract.getOrders(null)

        assertNotNull(result)
        assertEquals(3, result.size)
    }

    @Test
    fun `should throw NoObjectFoundException when no orders are found`() {
        whenever(orderGateway.findAll()).thenReturn(emptyList())

        assertThrows<NoObjectFoundException> {
            listOrdersInteract.getOrders(null)
        }
    }

    private fun createOrderItems(): List<OrderItem> {
        return listOf(
            OrderItem(1L, 2, "Extra cheese", true),
            OrderItem(2L, 1, null, false)
        )
    }
}

