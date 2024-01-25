package br.com.fiap.postech.foodchallenge.application.usecases.product

import br.com.fiap.postech.foodchallenge.application.gateways.ProductGateway
import br.com.fiap.postech.foodchallenge.domain.entities.Category
import br.com.fiap.postech.foodchallenge.domain.entities.Product
import br.com.fiap.postech.foodchallenge.domain.exceptions.ProductAlreadyExistsException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever

class CreateProductInteractTest {

    private lateinit var gateway: ProductGateway
    private lateinit var createProductInteract: CreateProductInteract

    @BeforeEach
    fun setUp() {
        gateway = mock()
        createProductInteract = CreateProductInteract(gateway)
    }

    @Test
    fun `should create product when no existing product with same name`() {
        val newProduct = Product(
            name = "New Product",
            description = "Description",
            image = "Image URL",
            price = 100,
            category = Category.MAIN
        )
        whenever(gateway.findByName(newProduct.name)).thenReturn(null)

        createProductInteract.createProduct(newProduct)

        verify(gateway).save(newProduct)
    }

    @Test
    fun `should throw ProductAlreadyExistsException for existing product name`() {
        val existingProduct = Product(
            name = "Existing Product",
            description = "Description",
            image = "Image URL",
            price = 100,
            category = Category.MAIN
        )
        whenever(gateway.findByName(existingProduct.name)).thenReturn(existingProduct)

        assertThrows<ProductAlreadyExistsException> {
            createProductInteract.createProduct(existingProduct)
        }
    }
}