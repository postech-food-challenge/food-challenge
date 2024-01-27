package br.com.fiap.postech.foodchallenge.application.usecases.order

import br.com.fiap.postech.foodchallenge.application.gateways.OrderGateway
import br.com.fiap.postech.foodchallenge.domain.entities.order.Order
import br.com.fiap.postech.foodchallenge.domain.entities.order.OrderStatus
import br.com.fiap.postech.foodchallenge.domain.exceptions.InvalidParameterException
import br.com.fiap.postech.foodchallenge.domain.exceptions.NoObjectFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class UpdateOrderStatusInteractTest {

    private lateinit var orderGateway: OrderGateway
    private lateinit var updateOrderStatusInteract: UpdateOrderStatusInteract

    @BeforeEach
    fun setUp() {
        orderGateway = mock()
        updateOrderStatusInteract = UpdateOrderStatusInteract(orderGateway)
    }

    @Test
    fun `should update order status when order exists`() {
        val orderId = 1L
        val existingOrder = Order(id = orderId, items = listOf(), status = OrderStatus.RECEIVED)
        val newStatus = OrderStatus.COMPLETED.name
        val updatedOrder = existingOrder.withUpdatedStatus(newStatus)

        whenever(orderGateway.findById(orderId)).thenReturn(existingOrder)
        whenever(orderGateway.save(any())).thenReturn(updatedOrder)

        val result = updateOrderStatusInteract.updateOrderStatus(orderId, newStatus)

        assertNotNull(result)
        assertEquals(OrderStatus.COMPLETED, result.status)
        verify(orderGateway).save(updatedOrder)
    }

    @Test
    fun `should throw NoObjectFoundException when order does not exist`() {
        val nonExistentOrderId = 2L
        val newStatus = OrderStatus.CANCELED.name

        whenever(orderGateway.findById(nonExistentOrderId)).thenReturn(null)

        assertThrows<NoObjectFoundException> {
            updateOrderStatusInteract.updateOrderStatus(nonExistentOrderId, newStatus)
        }
    }

    @Test
    fun `should throw InvalidParameterException for invalid order status`() {
        val orderId = 1L
        val existingOrder = Order(id = orderId, items = listOf(), status = OrderStatus.RECEIVED)
        val invalidStatus = "INVALID_STATUS"

        whenever(orderGateway.findById(orderId)).thenReturn(existingOrder)

        assertThrows<InvalidParameterException> {
            updateOrderStatusInteract.updateOrderStatus(orderId, invalidStatus)
        }
    }
}
