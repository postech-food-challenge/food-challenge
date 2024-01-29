package br.com.fiap.postech.foodchallenge.application.usecases.payment

import br.com.fiap.postech.foodchallenge.application.gateways.MercadoPagoGateway
import br.com.fiap.postech.foodchallenge.application.gateways.ProductGateway
import br.com.fiap.postech.foodchallenge.domain.entities.Category
import br.com.fiap.postech.foodchallenge.domain.entities.Product
import br.com.fiap.postech.foodchallenge.domain.entities.order.OrderItem
import br.com.fiap.postech.foodchallenge.infrastructure.client.mercadopago.MercadoPagoRequest
import br.com.fiap.postech.foodchallenge.infrastructure.client.mercadopago.MercadoPagoResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class CreatePaymentInteractTest {

    private lateinit var productGateway: ProductGateway
    private lateinit var mercadoPagoGateway: MercadoPagoGateway
    private lateinit var createPaymentInteract: CreatePaymentInteract

    @BeforeEach
    fun beforeEach(){
        productGateway = mock()
        mercadoPagoGateway = mock()
        createPaymentInteract = CreatePaymentInteract(productGateway, mercadoPagoGateway)
    }

    @Test
    fun `should successfully create a payment`() {
        val product = Product(1L, "Hamburguer", "Delicious Hamburguer", "image.png", 10, Category.MAIN)
        val mercadoPagoResponse = MercadoPagoResponse(qrData = "AA", inStoreOrderId = "123")

        whenever(productGateway.findById(1L)).thenReturn(product)
        whenever(mercadoPagoGateway.createPayment(any())).thenReturn(mercadoPagoResponse)

        val result = createPaymentInteract.createPayment(createOrderItems())

        assertEquals(20, result.price)
    }


    private fun createOrderItems(): List<OrderItem> {
        return listOf(
            OrderItem(1L, 2, "Extra cheese", true),
        )
    }
}