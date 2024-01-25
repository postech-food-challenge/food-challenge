package br.com.fiap.postech.foodchallenge.application.usecases.product

import br.com.fiap.postech.foodchallenge.application.gateways.ProductGateway
import br.com.fiap.postech.foodchallenge.domain.entities.Category
import br.com.fiap.postech.foodchallenge.domain.entities.Product
import br.com.fiap.postech.foodchallenge.domain.exceptions.ProductNotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class UpdateProductInteractTest {

    private lateinit var gateway: ProductGateway
    private lateinit var updateProductInteract: UpdateProductInteract

    @BeforeEach
    fun setUp() {
        gateway = mock()
        updateProductInteract = UpdateProductInteract(gateway)
    }

    @Test
    fun `should update product when it exists`() {
        val productId = 1L
        val existingProduct = Product(
            id = productId,
            name = "Existing Product",
            description = "Description",
            image = "Image URL",
            price = 100,
            category = Category.MAIN
        )
        val updatedProduct = Product(
            id = productId,
            name = "Updated Product",
            description = "New Description",
            image = "New Image URL",
            price = 150,
            category = Category.SIDE
        )

        whenever(gateway.findById(productId)).thenReturn(existingProduct)
        whenever(gateway.save(any())).thenReturn(updatedProduct)

        val result = updateProductInteract.updateProduct(productId, updatedProduct)

        assertEquals(updatedProduct, result)
        verify(gateway).save(updatedProduct)
    }

    @Test
    fun `should throw ProductNotFoundException when product does not exist`() {
        val nonExistentProductId = 2L
        val newProduct = Product(
            id = nonExistentProductId,
            name = "New Product",
            description = "Description",
            image = "Image URL",
            price = 100,
            category = Category.DRINK
        )

        whenever(gateway.findById(nonExistentProductId)).thenReturn(null)

        assertThrows<ProductNotFoundException> {
            updateProductInteract.updateProduct(nonExistentProductId, newProduct)
        }
    }
}
