package br.com.fiap.postech.foodchallenge.application.usecases.product

import br.com.fiap.postech.foodchallenge.application.gateways.ProductGateway
import br.com.fiap.postech.foodchallenge.domain.entities.Category
import br.com.fiap.postech.foodchallenge.domain.entities.Product
import br.com.fiap.postech.foodchallenge.domain.exceptions.ProductNotFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class DeleteProductInteractTest {

    private lateinit var gateway: ProductGateway
    private lateinit var deleteProductInteract: DeleteProductInteract

    @BeforeEach
    fun setUp() {
        gateway = mock()
        deleteProductInteract = DeleteProductInteract(gateway)
    }

    @Test
    fun `should delete product when product exists`() {
        val productId = 1L
        val product = Product(
            id = productId,
            name = "Test Product",
            description = "Description",
            image = "Image URL",
            price = 100,
            category = Category.MAIN
        )
        whenever(gateway.findById(productId)).thenReturn(product)

        deleteProductInteract.deleteProduct(productId)

        verify(gateway).delete(product)
    }

    @Test
    fun `should throw ProductNotFoundException when product does not exist`() {
        val productId = 1L
        whenever(gateway.findById(productId)).thenReturn(null)

        assertThrows<ProductNotFoundException> {
            deleteProductInteract.deleteProduct(productId)
        }

        verify(gateway, never()).delete(any())
    }
}