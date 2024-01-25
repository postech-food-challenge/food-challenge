package br.com.fiap.postech.foodchallenge.application.usecases.product

import br.com.fiap.postech.foodchallenge.application.gateways.ProductGateway
import br.com.fiap.postech.foodchallenge.domain.entities.Category
import br.com.fiap.postech.foodchallenge.domain.entities.Product
import br.com.fiap.postech.foodchallenge.domain.exceptions.InvalidParameterException
import br.com.fiap.postech.foodchallenge.domain.exceptions.NoObjectFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class FindProductByCategoryInteractTest {

    private lateinit var gateway: ProductGateway
    private lateinit var findProductByCategoryInteract: FindProductByCategoryInteract

    @BeforeEach
    fun setUp() {
        gateway = mock()
        findProductByCategoryInteract = FindProductByCategoryInteract(gateway)
    }

    @Test
    fun `should return list of products for valid category`() {
        val category = "MAIN"
        val products = listOf(Product(1L, "Product A", "Description", "Image URL", 100, Category.MAIN))
        whenever(gateway.findByCategory(Category.valueOf(category))).thenReturn(products)

        val result = findProductByCategoryInteract.findProductByCategory(category)

        assertEquals(products, result)
    }

    @Test
    fun `should throw NoObjectFoundException for category with no products`() {
        val category = "MAIN"
        whenever(gateway.findByCategory(Category.valueOf(category))).thenReturn(emptyList())

        assertThrows<NoObjectFoundException> {
            findProductByCategoryInteract.findProductByCategory(category)
        }
    }

    @Test
    fun `should throw InvalidParameterException for invalid category`() {
        val invalidCategory = "INVALID_CATEGORY"

        assertThrows<InvalidParameterException> {
            findProductByCategoryInteract.findProductByCategory(invalidCategory)
        }
    }
}
