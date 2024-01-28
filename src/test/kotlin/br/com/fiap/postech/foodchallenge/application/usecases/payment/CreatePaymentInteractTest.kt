package br.com.fiap.postech.foodchallenge.application.usecases.payment

import br.com.fiap.postech.foodchallenge.application.gateways.ProductGateway
import br.com.fiap.postech.foodchallenge.domain.entities.Category
import br.com.fiap.postech.foodchallenge.domain.entities.Product
import br.com.fiap.postech.foodchallenge.domain.entities.order.OrderItem
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class CreatePaymentInteractTest {

    private lateinit var productGateway: ProductGateway
    private lateinit var createPaymentInteract: CreatePaymentInteract

    @BeforeEach
    fun beforeEach(){
        productGateway = mock()
        createPaymentInteract = CreatePaymentInteract(productGateway)
    }

    @Test
    fun `should successfully create a payment`() {
        val product = Product(1L, "Hamburguer", "Delicious Hamburguer", "image.png", 10, Category.MAIN)

        whenever(productGateway.findById(1L)).thenReturn(product)

        val result = createPaymentInteract.createPayment(createOrderItems())

        assertEquals(20, result)
    }


    private fun createOrderItems(): List<OrderItem> {
        return listOf(
            OrderItem(1L, 2, "Extra cheese", true),
        )
    }
}