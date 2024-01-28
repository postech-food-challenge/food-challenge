package br.com.fiap.postech.foodchallenge.application.usecases.payment

import br.com.fiap.postech.foodchallenge.application.gateways.PaymentGateway
import br.com.fiap.postech.foodchallenge.domain.entities.Payment
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class FindPaymentByOrderInteractTest {

    private lateinit var gateway: PaymentGateway
    private lateinit var findPaymentByOrderIdInteract: FindPaymentByOrderIdInteract

    @BeforeEach
    fun setUp() {
        gateway = mock()
        findPaymentByOrderIdInteract = FindPaymentByOrderIdInteract(gateway)
    }

    @Test
    fun `should successfully get a payment`() {
        val payment = Payment(orderId = 1L , paymentValidated = true)
        whenever(gateway.findByOrderId(payment.orderId)).thenReturn(payment)

        val result = findPaymentByOrderIdInteract.findPaymentByOrderId(payment.orderId)

        assertEquals(payment, result)
    }
}